package com.razgailova.currencyexchange.data.server;

/**
 * Created by Катерина on 22.11.2017.
 */

public class ServerApiImpl implements ServerApi {
    public static final String EXCHANGE_RATES_URL = "http://www.cbr.ru/scripts/XML_daily.asp";
    public static final String EXCHANGE_RATES_CHARSET = "windows-1251";

    private ServerWorker serverWorker;

    public ServerApiImpl() {
        serverWorker = new ServerWorker();
    }

    @Override
    public String getExchangeRates() throws Exception {
        return serverWorker.getXMLFromUrl(EXCHANGE_RATES_URL, EXCHANGE_RATES_CHARSET);
    }




}
