package br.com.luisjustin.api.mobilidade.jobs;

import br.com.luisjustin.api.mobilidade.model.BusLine;
import br.com.luisjustin.api.mobilidade.model.BusMap;
import br.com.luisjustin.api.mobilidade.model.json.BusLineJson;
import br.com.luisjustin.api.mobilidade.repository.BusLineRepository;
import java.lang.String;

import br.com.luisjustin.api.mobilidade.repository.BusMapRepository;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Iterator;
import java.util.Map;

public class DataPoaApiCheckJob extends QuartzJobBean {

    private static final String USER_AGENT = "Mozila/5.0";

    @Autowired
    private BusLineRepository buslineRepo;

    @Autowired
    private BusMapRepository busmapRepo;

    private Gson gson = new Gson();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        try {
            fetchBusLinesAndSaveIt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("FIMMMMMMM");

    }

    private void fetchBusMapAndSaveIt(int id) throws IOException {
        URL obj = null;
        obj = new URL("http://www.poatransporte.com.br/php/facades/process.php?a=il&p=" + id);
        HttpURLConnection con = null;
        con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int response_code = 0;
        response_code = con.getResponseCode();
        if( response_code == HttpURLConnection.HTTP_OK ) {
            StringBuffer response = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonFactory factory = new JsonFactory();

            ObjectMapper mapper = new ObjectMapper(factory);
            JsonNode rootNode = mapper.readTree(response.toString());
            Iterator<Map.Entry<String, JsonNode>> fieldsIterator = rootNode.fields();
            String idlinha;
            String nome;
            String codigo;

            busmapRepo.deleteByBusLineId(buslineRepo.findByLineId(id).getId());

            while( fieldsIterator.hasNext() ) {
                Map.Entry<String, JsonNode> field = fieldsIterator.next();
                if( field.getKey() != "idlinha" && field.getKey() != "nome" && field.getKey() != "codigo" ) {

                    BusMap bMap = new BusMap();
                    bMap.setPosition(Long.valueOf(field.getKey()));
                    bMap.setBusLine(buslineRepo.findByLineId(id));
                    bMap.setLat(Double.valueOf(field.getValue().get("lat").toString().replace("\"", "")));
                    bMap.setLng(Double.valueOf(field.getValue().get("lng").toString().replace("\"", "")));
                    busmapRepo.save(bMap);

                }else if( field.getKey() == "idlinha" ) {
                    idlinha = field.getValue().toString();
                }else if( field.getKey() == "nome") {
                    nome = field.getValue().toString();
                }else if( field.getKey() == "codigo" ) {
                    codigo = field.getValue().toString();
                }
            }

        }



    }

    private void fetchBusLinesAndSaveIt() throws IOException {
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

                try {
                    BusLine bl = buslineRepo.findByLineId(b.getId());
                    bl.setLineCode(b.getCodigo());
                    bl.setLineName(b.getNome());
                    buslineRepo.save(bl);
                }catch (Exception ex){
                    BusLine busLine = new BusLine();
                    busLine.setLineId(b.getId());
                    busLine.setLineCode(b.getCodigo());
                    busLine.setLineName(b.getNome());
                    buslineRepo.save(busLine);
                }

                fetchBusMapAndSaveIt(b.getId());

            }

        }else{
            System.out.println("GET Request not worked");
        }
    }
}
