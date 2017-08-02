package com.altcoin.trading.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.altcoin.base.vo.UserHeader;
import com.altcoin.outside.account.balance.service.BalanceService;
import com.altcoin.trading.vo.CoinoneVo;
import com.altcoin.util.HttpUtil;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

@Controller
public class AltCoinAPIController {

    @Resource(name = "balanceService")
    private BalanceService balanceService;
    
    // 한국
    private static final String COINONE_API_TICKER_URI = "https://api.coinone.co.kr/ticker/";
    private static final String COINONE_API_ORDERBOOK_URI = "https://api.coinone.co.kr/orderbook/";

    private static final String BITHUMB_API_TICKER_URI = "https://api.bithumb.com/public/ticker/";

    private static final String KORBIT_API_TICKER_URI = "https://api.korbit.co.kr/v1/ticker";

    // 미국
    private static final String BITTREX_API_TICKER_URI = "https://bittrex.com/api/v1.1/public/getticker/";
    private static final String BITTREX_API_ORDERBOOK_URI = "https://bittrex.com/api/v1.1/public/getorderbook/";

    private static final String POLONIEX_API_TICKER_URI = "https://poloniex.com/public?command=returnTicker";


    public static final int TIME_OUT = 10 * 1000;
    public static final int DIFFERENCE = 100000;

    public static final String COIN_XRP = "xrp";

    RestTemplate restTemplate = new RestTemplate();
    List<ResponseEntity<JSONObject>> responseEntity = null;
    
    @RequestMapping(value="/loginPage")
    public String loginFrm(HttpServletRequest request, Model model) {
    	
    	return "login";
    }
    
    @RequestMapping(value="/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model, UserHeader header) throws Exception {
    	
    	String userId = header.getUserId();
    	String userPassword = header.getUserPassword();
    	String returnUrl = "redirect:loginPage";
    	
    	if(HttpUtil.isNullCheck(userId) && HttpUtil.isNullCheck(userPassword) && userId.equals("test") && userPassword.equals("1234")){
    		String coinone_access_token = "887d9c35-7146-4e8c-85a3-4732c3a902b7";
            String poloniex_access_token = "4LL1N1VM-ZUEMUNL8-91HGG6WB-3SEE0PHU";
            String coinone_secret = "44bd42a6-e5af-47c0-b99f-a3e7ebe361e1";
            String poloniex_secret = "a86eab1fd1a98dc75214103d0519d0afa9b59960d71e9fb0890da7daad93a90c77d8df67296928ccb3b5ff16ecfc8c264e0f69a1382ae934b9ae10fc2bbaff44";

            header = new UserHeader();

            // 인터셉터로 옮겨서 처리
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("access_token", coinone_access_token);
            map.put("nonce", HttpUtil.getNonce());

            header.setCoinoneApikeys(map);

            map = new HashMap<String, Object>();
            map.put("access_token", poloniex_access_token);	
            map.put("nonce", HttpUtil.getNonceFull());

            header.setPoloniexApikeys(map);

            header.setCoinoneSecert(coinone_secret);
            header.setPoloniexSecert(poloniex_secret);

            header.setCoin(COIN_XRP);
            
            request.getSession().setAttribute("userInfo", header);
            
            returnUrl = "redirect:main";
    	}
    	
    	return returnUrl;
    }
    
    @RequestMapping(value="/main")
    public String main(HttpServletRequest request, HttpServletResponse response, Model model, UserHeader header) throws Exception {
    	return "main";
    }

    @RequestMapping(value = "/api/main", method=RequestMethod.GET)
    public @ResponseBody Map<String , Object> mainJson(HttpServletRequest request, Model model, UserHeader header) throws Exception {
        // 타임아웃 걸릴 시 예외처리 방안
        /*HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(TIME_OUT);
        factory.setReadTimeout(TIME_OUT);*/
    	
    	Map<String , Object> maps = new HashMap<String, Object>();

    	String[] siteMap = {"COINONE", "BITTREX", "POLONIEX"};

        viewDataSetting(siteMap, model);
        
        header = (UserHeader) request.getSession().getAttribute("userInfo");
        
        maps.put("coinoneBalance", balanceService.selectCoinoneBalance(header));
        maps.put("poloniexBalance", balanceService.selectPoloniexBalance(header));
        
        return maps;
    }

    private void viewDataSetting(String[] siteMap, Model model){
        List<String> coins = new ArrayList<String>();
        List<List<ResponseEntity<JSONObject>>> list = new ArrayList<List<ResponseEntity<JSONObject>>>();

        String url = "";
        String param1 = "";
        String param2 = "";

        for(String site : siteMap){
            coins = getCoins(site);

            if(site == "COINONE"){
                url = COINONE_API_TICKER_URI;
                param1 = "currency";
                param2 = "";
            } else if(site == "BITHUMB"){
                url = BITHUMB_API_TICKER_URI;
                param1 = "";
                param2 = "";
            } else if(site == "BITTREX"){
                url = BITTREX_API_TICKER_URI;
                param1 = "market";
                param2 = "BTC";
            } else if(site == "POLONIEX"){
                url = POLONIEX_API_TICKER_URI;
                param1 = "";
                param2 = "";
            }

            list.add(getData(url, param1, param2, site, coins));

        }

        model.addAttribute("dataList", list);
    }

    private List<String> getCoins(String site){
        List<String> coins = new ArrayList<String>();

        site = site.toUpperCase();

        coins.add("BTC");
        coins.add("ETH");
        coins.add("ETC");
        coins.add("XRP");

        if(site == "BITHUMB"){
            coins.add("DASH");
            coins.add("LTC");
        } else if(site == "BITTREX"){
            coins.add("DASH");
            coins.add("LTC");
            coins.add("SNT");
            coins.add("ANS");
        }


        return coins;
    }

    private List<ResponseEntity<JSONObject>> getData(String url, String param1, String param2, String site, List<String> coins){

        List<ResponseEntity<JSONObject>> responseDatas = new ArrayList<ResponseEntity<JSONObject>>();

        String requestUrl = "";

        if(site == "POLONIEX"){
            /*ResponseEntity<JSONObject> data = restTemplate.getForEntity(url, JSONObject.class);

            JSONObject jsonObj = data.getBody();

            Iterator iterator = (Iterator) jsonObj.keySet().iterator();

            while(iterator.hasNext()){
                String key = (String) iterator.next();
                String value = (String) jsonObj.get(key);

                System.out.println(key + " : " + value);
            }

            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();

            responseDatas.add(data);*/
        } else {
            for(String coin : coins){
                if(!param1.equals("") && !param2.equals("")){
                    requestUrl = url + "?" + param1 + "=" + param2 + "-" + coin;
                } else if(!param1.equals("")){
                    requestUrl = url + "?" + param1 + "=" + coin;
                } else{
                    requestUrl = url + coin;
                }

                ResponseEntity<JSONObject> data = restTemplate.getForEntity(requestUrl, JSONObject.class);

                responseDatas.add(data);
            }
        }

        return responseDatas;
    }






















    @RequestMapping(value = "/api/premiumCalcJson")
    public String premiumCalcJson(HttpServletRequest request, Model model) throws Exception {
        String totalPrice = request.getParameter("totalPrice") != null && !request.getParameter("totalPrice").equals("")? request.getParameter("totalPrice") : "2556963";
        int btcPrice = 0;
        Double btcCount = 0D;
        int xrpPrice = 0;
        Double xrpCount = 0D;

        ResponseEntity<CoinoneVo> coinoneResponse = null;

        coinoneResponse = restTemplate.getForEntity(COINONE_API_ORDERBOOK_URI + "?currency=btc", CoinoneVo.class);

        Float sum = (float) 0;

        for(Object obj : coinoneResponse.getBody().getAsk()){
            CoinoneVo vo = (CoinoneVo) obj;

            sum += (Integer.parseInt(vo.getPrice()) * Float.parseFloat(vo.getQty()));

            if(Integer.parseInt(totalPrice) < sum){
                btcPrice = Integer.parseInt(vo.getPrice());
                btcCount = Double.parseDouble(totalPrice) / Integer.parseInt(vo.getPrice());

                break;
            }
        }

        coinoneResponse = restTemplate.getForEntity(COINONE_API_ORDERBOOK_URI + "?currency=xrp", CoinoneVo.class);

        for(Object obj : coinoneResponse.getBody().getAsk()){
            CoinoneVo vo = (CoinoneVo) obj;

            sum += (Integer.parseInt(vo.getPrice()) * Float.parseFloat(vo.getQty()));

            if(Integer.parseInt(totalPrice) < sum){
                xrpPrice = Integer.parseInt(vo.getPrice());
                xrpCount = Double.parseDouble(totalPrice) / Integer.parseInt(vo.getPrice());

                break;
            }
        }

        coinoneResponse = restTemplate.getForEntity(COINONE_API_ORDERBOOK_URI + "?currency=xrp", CoinoneVo.class);

        for(Object obj : coinoneResponse.getBody().getAsk()){
            CoinoneVo vo = (CoinoneVo) obj;

            sum += (Integer.parseInt(vo.getPrice()) * Float.parseFloat(vo.getQty()));

            if(Integer.parseInt(totalPrice) < sum){
                xrpPrice = Integer.parseInt(vo.getPrice());
                xrpCount = Double.parseDouble(totalPrice) / Integer.parseInt(vo.getPrice());

                break;
            }
        }

        ResponseEntity<String> bittrexResponse = null;
        bittrexResponse = restTemplate.getForEntity(BITTREX_API_ORDERBOOK_URI + "?market=usdt-btc&type=both", String.class);


        JSONParser jsonParse = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParse.parse(bittrexResponse.getBody());
        JSONObject jsonBuyObj = (JSONObject) jsonObj.get("result");

        JSONArray jsonArray = (JSONArray) jsonBuyObj.get("buy");

        Double btcusdt = 0D;
        Double btcBalance = btcCount;

        for(int i=0; i<jsonArray.size(); i++){
            JSONObject obj = (JSONObject) jsonArray.get(i);

            if(Double.parseDouble(String.valueOf(obj.get("Quantity"))) > btcBalance){
                btcusdt = Double.parseDouble(String.valueOf(obj.get("Rate"))) * btcCount;
                break;
            } else {
                Double qty = Double.parseDouble(String.valueOf(obj.get("Quantity")));

                if(qty > btcBalance){
                    qty = btcBalance;
                }

                btcusdt += Double.parseDouble(String.valueOf(obj.get("Rate"))) * qty;
                btcBalance -= qty;
            }
        }

        // System.out.println(btcusdt);

        bittrexResponse = restTemplate.getForEntity(BITTREX_API_ORDERBOOK_URI + "?market=btc-xrp&type=both", String.class);

        jsonParse = new JSONParser();
        jsonObj = (JSONObject) jsonParse.parse(bittrexResponse.getBody());
        jsonBuyObj = (JSONObject) jsonObj.get("result");

        jsonArray = (JSONArray) jsonBuyObj.get("buy");

        Double xrpBtc = 0D;
        Double xrpBalance = xrpCount;

        for(int i=0; i<jsonArray.size(); i++){
            JSONObject obj = (JSONObject) jsonArray.get(i);

            if(Double.parseDouble(String.valueOf(obj.get("Quantity"))) > xrpBalance){
                xrpBtc = Double.parseDouble(String.valueOf(obj.get("Rate"))) * xrpCount;
                break;
            } else {
                Double qty = Double.parseDouble(String.valueOf(obj.get("Quantity")));

                if(qty > xrpBalance){
                    qty = xrpBalance;
                }

                xrpBtc += Double.parseDouble(String.valueOf(obj.get("Rate"))) * qty;
                xrpBalance -= qty;
            }
        }

        // System.out.println(xrpBtc);

        bittrexResponse = restTemplate.getForEntity(BITTREX_API_ORDERBOOK_URI + "?market=usdt-btc&type=both", String.class);

        jsonParse = new JSONParser();
        jsonObj = (JSONObject) jsonParse.parse(bittrexResponse.getBody());
        jsonBuyObj = (JSONObject) jsonObj.get("result");

        jsonArray = (JSONArray) jsonBuyObj.get("buy");

        Double xrpusdt = 0D;
        xrpBalance = xrpBtc;

        for(int i=0; i<jsonArray.size(); i++){
            JSONObject obj = (JSONObject) jsonArray.get(i);

            if(Double.parseDouble(String.valueOf(obj.get("Quantity"))) > xrpBalance){
                xrpusdt = Double.parseDouble(String.valueOf(obj.get("Rate"))) * xrpBtc;
                break;
            } else {
                Double qty = Double.parseDouble(String.valueOf(obj.get("Quantity")));

                if(qty > xrpBalance){
                    qty = xrpBalance;
                }

                xrpusdt += Double.parseDouble(String.valueOf(obj.get("Rate"))) * qty;
                xrpBalance -= qty;
            }
        }

        Double btcDifference = 0D;
        Double xrpDifference = 0D;

        // System.out.println(xrpusdt);
        System.out.print("비트코인 시가 차액 : ");
        if(Double.parseDouble(totalPrice) > (btcusdt * 1127.35)){
            btcDifference = (btcusdt * 1127.35) - Double.parseDouble(totalPrice);
        } else {
            btcDifference = Double.parseDouble(totalPrice) - (btcusdt * 1127.35);
        }
        System.out.println(btcDifference);
        System.out.print("리플 시가 차액 : ");
        if(Double.parseDouble(totalPrice) > (xrpusdt * 1127.35)){
            xrpDifference = (xrpusdt * 1127.35) - Double.parseDouble(totalPrice);
        } else {
            xrpDifference = Double.parseDouble(totalPrice) - (xrpusdt * 1127.35);
        }
        System.out.println(xrpDifference);

        if(btcDifference > DIFFERENCE || xrpDifference > DIFFERENCE){
            sendMail(btcDifference, xrpDifference);
        }

        model.addAttribute("btcusdt", "btcusdt");
        model.addAttribute("xrpBtc", "xrpBtc");
        model.addAttribute("xrpusdt", "xrpusdt");

        return "home/sitemap/orderbook";

    }

    public static void sendMail(Double btc, Double xrp){
        Properties properties = new Properties();
        properties.put("mail.smtp.user", "senderEmail@gmail.com"); //구글 계정
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.debug", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");

        try {
            Authenticator auth = new senderAccount();
            Session session = Session.getInstance(properties, auth);
            session.setDebug(true); // 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.
            MimeMessage msg = new MimeMessage(session);

            msg.setSubject("경고!! BTC : "+Double.parseDouble(String.format("%.2f", btc))+" XRP : "+Double.parseDouble(String.format("%.2f", xrp)));
            Address fromAddr = new InternetAddress("jhjssj3@gmail.com"); // 보내는사람 EMAIL
            msg.setFrom(fromAddr);
            Address toAddr = new InternetAddress("jhjssj3@naver.com");    //받는사람 EMAIL
            msg.addRecipient(Message.RecipientType.TO, toAddr);
            msg.setContent("메일에 전송될 내용", "text/plain;charset=KSC5601"); //메일 전송될 내용
            Transport.send(msg);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class senderAccount extends javax.mail.Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("jhjssj3", "tjswo3152"); // @gmail.com 제외한 계정 ID, PASS

        }
    }
}