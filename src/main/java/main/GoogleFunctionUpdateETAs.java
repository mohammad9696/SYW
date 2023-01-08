package main;

import Services.UpdateProductETA;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

import java.io.BufferedWriter;

//can't run unless with this:
//mvn function:run -Drun.functionTarget=main.GoogleFunctionUpdateFeeds.service
public class GoogleFunctionUpdateETAs implements HttpFunction {

    public void service(HttpRequest request, HttpResponse response) throws Exception {
        BufferedWriter writer = response.getWriter();
        writer.write("Updating ETAs!");
        UpdateProductETA.updateAllProductsEta(null);
    }
}
