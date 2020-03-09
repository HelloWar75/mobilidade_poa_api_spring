package br.com.luisjustin.api.mobilidade.jobs;

import br.com.luisjustin.api.mobilidade.model.json.BusLineJson;
import br.com.luisjustin.api.mobilidade.repository.BusLineRepository;
import java.lang.String;

import com.google.gson.Gson;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class DataPoaApiCheckJob extends QuartzJobBean {

    private static final String USER_AGENT = "Mozila/5.0";

    @Autowired
    private BusLineRepository buslineRepo;

    private Gson gson = new Gson();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        //Começar puxando todos
        URL obj = null;
        try {
            obj = new URL("http://www.poatransporte.com.br/php/facades/process.php?a=nc&p=%&t=o");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) obj.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = 0;
        try {
            responseCode = con.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(responseCode == HttpURLConnection.HTTP_OK) {
            StringBuffer response = null;
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                response = new StringBuffer();

                while((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            BusLineJson[] bus_line_json = gson.fromJson(response.toString(), BusLineJson[].class);

            for(BusLineJson b : bus_line_json) {
                System.out.println("Nome: " + b.getNome() );
            }

        }else{
            System.out.println("GET Request not worked");
        }

    }
}
