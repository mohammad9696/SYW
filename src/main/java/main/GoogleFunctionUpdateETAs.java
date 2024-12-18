package main;

import Services.ShopifyProductMetafieldsManager;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

import java.io.BufferedWriter;
import java.util.NoSuchElementException;

//can't run unless with this:
//mvn function:run -Drun.functionTarget=main.GoogleFunctionUpdateFeeds.service
public class GoogleFunctionUpdateETAs implements HttpFunction {

    public void service(HttpRequest request, HttpResponse response) throws Exception {
        BufferedWriter writer = response.getWriter();
        writer.write("Updating ETAs!");
        ShopifyProductMetafieldsManager.updateAllProductsEta(request.getFirstQueryParameter("sku").orElseThrow(() -> new NoSuchElementException("SKU parameter is missing")).split(","));
    }
}
