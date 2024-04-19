package Services;

import Constants.ProductMetafieldEnum;
import DTO.ProductDTO;
import DTO.ProductImageDTO;
import DTO.ProductMetafieldDTO;
import DTO.ProductObjectDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ProductLaunchService {

    public boolean doesProductMeetRequirementsToLaunch(ProductDTO productToLaunch, ProductMetafieldDTO metaTitle, ProductMetafieldDTO metaDescription){
        boolean isValid = true;

        if (productToLaunch.getTitle().isEmpty()){
            System.out.println("Title can't be empty!");
            isValid = false;
        }

        if (productToLaunch.sku().isEmpty() || productToLaunch.sku().equals("")){
            System.out.println("SKU inválido");
            isValid = false;
        }

        if (productToLaunch.barcode() == null || productToLaunch.barcode().isEmpty() || productToLaunch.barcode().equals("")){
            System.out.println("EAN inválido");
            isValid = false;
        }

        if (productToLaunch.getProductType().isEmpty() || productToLaunch.getProductType().equals("") ){
            System.out.println("Tipo de produto vazio");
            isValid = false;
        }

        if (productExistsShopify(productToLaunch.sku())){
            System.out.println("Produto existente no shopify");
            isValid = false;
        }

        if (!productToLaunch.getTitle().toLowerCase(Locale.ROOT).contains(productToLaunch.getBrand().toLowerCase(Locale.ROOT))){
            System.out.println("Título não tem nome da marca");
            isValid = false;
        }

        if (!productToLaunch.getHandle().toLowerCase(Locale.ROOT).contains(productToLaunch.getBrand().toLowerCase(Locale.ROOT))){
            System.out.println("Handle não tem nome da marca");
            isValid = false;
        }

        if (productToLaunch.getHandle().toLowerCase(Locale.ROOT).contains("copy")){
            System.out.println("Handle tem copy");
            isValid = false;
        }

        if (productToLaunch.getBodyHtml().toLowerCase(Locale.ROOT).contains("<h2")){
            if(!productToLaunch.getBodyHtml().toLowerCase(Locale.ROOT).split("</h2>")[0].contains("font-size") ||
                    !productToLaunch.getBodyHtml().toLowerCase(Locale.ROOT).split("</h2>")[0].contains("15")){
                System.out.println("Falta o h2 com 15px");
                isValid = false;
            }
        } else {
            System.out.println("Falta o h2 com 15px");
            isValid = false;
        }

        if (!productToLaunch.getBodyHtml().toLowerCase(Locale.ROOT).contains("inclui")){
            System.out.println("Secção Inclui em falta");
            isValid = false;
        }


        if (productToLaunch.getBodyHtml().toLowerCase(Locale.ROOT).contains("características do produto")){
            if (!productToLaunch.getBodyHtml().toLowerCase(Locale.ROOT).split("características do produto")[1].contains("marca")){
                System.out.println("Marca em falta nas características do produto");
                isValid = false;
            }

            if (!productToLaunch.getBodyHtml().toLowerCase(Locale.ROOT).split("características do produto")[1].contains("modelo")){
                System.out.println("Modelo em falta nas características do produto");
                isValid = false;
            }

            if (!productToLaunch.getBodyHtml().toLowerCase(Locale.ROOT).split("características do produto")[1].contains("aplicação")){
                System.out.println("Aplicação em falta nas características do produto");
                isValid = false;
            }

            if (!productToLaunch.getBodyHtml().toLowerCase(Locale.ROOT).split("características do produto")[1].contains("peso")){
                System.out.println("Peso em falta nas características do produto");
                isValid = false;
            }

            if (!productToLaunch.getBodyHtml().toLowerCase(Locale.ROOT).split("características do produto")[1].contains("dimensões")){
                System.out.println("Dimensões em falta nas características do produto");
                isValid = false;
            }

            if (!productToLaunch.getBodyHtml().toLowerCase(Locale.ROOT).split("características do produto")[1].contains("conectividade")){
                System.out.println("Conectividade em falta nas características do produto");
                isValid = false;
            }

            if (!productToLaunch.getBodyHtml().toLowerCase(Locale.ROOT).split("características do produto")[1].contains("compatível com")){
                System.out.println("Compatível com em falta nas características do produto");
                isValid = false;
            }
        } else {
            System.out.println("Secção Características do produto em falta");
            isValid = false;
        }


        if (productToLaunch.getImages().size()<3){
            System.out.println("O produto deverá conter no mínimo três imagens!");
            isValid = false;
        } else {
            for (ProductImageDTO i : productToLaunch.getImages()){
                if (!i.getSrc().toLowerCase(Locale.ROOT).contains(productToLaunch.getBrand().toLowerCase(Locale.ROOT))){
                    System.out.println("Image number " + i.getPosition() + " does not have brand in file name");
                    isValid = false;
                }

                if(i.getAltText() != null){
                    if (!i.getAltText().toLowerCase(Locale.ROOT).contains(productToLaunch.getBrand().toLowerCase(Locale.ROOT))){
                        System.out.println("Image number " + i.getPosition() + " alternate text does not have brand name");
                        isValid = false;
                    }
                    if (!i.getAltText().toLowerCase(Locale.ROOT).contains(productToLaunch.getProductType().toLowerCase(Locale.ROOT))){
                        System.out.println("Image number " + i.getPosition() + " alternate text does not have product type");
                        isValid = false;
                    }
                    if (i.getAltText().length() < 20){
                        System.out.println("Image number " + i.getPosition() + " alternate text deverá ter pelo menos 20 caracteres");
                        isValid = false;
                    }
                } else {
                    System.out.println("Texto alternativo da imagem  " + i.getPosition() + " não disponível");
                }
            }
        }

        if (productToLaunch.getVariants().get(0).getPrice() == null || productToLaunch.getVariants().get(0).getPrice() < 0.001){
            System.out.println("O produto deverá ter um preço definido");
            isValid = false;
        }

        if (productToLaunch.getVariants().get(0).getWeight() == null || productToLaunch.getVariants().get(0).getWeight() < 0.001){
            System.out.println("O produto deverá ter o peso definido");
            isValid = false;
        }

        if (metaTitle != null && metaTitle.getValue() != null && metaTitle.getValue() != ""){
            if (!metaTitle.getValue().toLowerCase(Locale.ROOT).contains(productToLaunch.getBrand().toLowerCase(Locale.ROOT))){
                System.out.println("Meta título não tem marca do produto");
                isValid = false;
            }
            if (metaTitle.getValue().length() < 40 || metaTitle.getValue().length() > 60 ){

                System.out.println("Meta título deverá ter entre 40 e 60 caracteres");
                isValid = false;
            }
        } else {
            System.out.println("Meta título vazio");
            isValid = false;
        }

        if (metaDescription != null && metaDescription.getValue() != null && metaDescription.getValue() != ""){
            if (!metaDescription.getValue().toLowerCase(Locale.ROOT).contains(productToLaunch.getBrand().toLowerCase(Locale.ROOT))){
                System.out.println("Meta descrição não tem marca do produto");
                isValid = false;
            }
            if (metaDescription.getValue().length() < 100 || metaDescription.getValue().length() > 160 ){

                System.out.println("Meta descrição deverá ter entre 100 e 160 caracteres");
                isValid = false;
            }
        } else {
            System.out.println("Meta descrição vazio");
            isValid = false;
        }

        Scanner scanner = new Scanner(System.in);
        if (!isValid){
            System.out.println("O produto não cumpre todos os requisitos para ser lançado. Pretende lançar na mesma?");
            System.out.println("Sim: Inserir password     Não: N");
            String pass = scanner.next();

            if (!pass.equals("OVERRIDE")){
                return false;
            } else {
                isValid = true;
            }
        }

        try {
            if (!MoloniService.existsProductMoloni(productToLaunch.sku())){
                System.out.println("Product does not exist in moloni! Create?");
                System.out.println("1: Yes    2: No");
                int option = scanner.nextInt();
                if (option == 1){
                    System.out.println("Have you checked if the product is not registed in Moloni with another SKU?");;
                    System.out.println("1: Yes    2: No");
                    option = scanner.nextInt();
                    if (option == 1){
                        System.out.println("Creating...");
                        boolean productCreated = MoloniService.createMoloniProduct(productToLaunch);
                        if (!productCreated || !MoloniService.existsProductMoloni(productToLaunch.sku())){
                            System.out.println("Erro a criar produto no moloni.");
                            isValid = false;
                        }
                    } else {
                        System.out.println("Exiting...");
                        isValid = false;
                    }
                } else {
                    isValid = false;
                }

            }
        } catch (Exception e){
            System.out.println("Erro genérico no moloni");
            isValid = false;
        }


        return isValid;
    }

    public boolean productExistsShopify(String sku){
        List<ProductDTO> shopifyProducts = ShopifyProductService.getShopifyProductList();
        for (ProductDTO check : shopifyProducts){
            if (check.sku().equals(sku)){
                System.out.println("Product Exists in Shopify: https://www.smartify.pt/" + check.getHandle());
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        List<ProductDTO> productsToLaunch = ShopifyProductService.getTestShopifyProductList();
        List<ProductDTO> productReadyToLaunch = new ArrayList<>();
        int i = 0;
        for (ProductDTO product : productsToLaunch){
            if (product.getStatus().toLowerCase(Locale.ROOT).equals("active")){
                productReadyToLaunch.add(product);
                System.out.println(i + " " +product.sku() +" "+ product.getTitle());
                i++;
            }
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha o produto a criar");
        int option = scanner.nextInt();
        ProductDTO productToLaunch = productReadyToLaunch.get(option);
        System.out.println("Escolhido: "+ productToLaunch.sku() +" "+ productToLaunch.getTitle());

        ProductLaunchService service = new ProductLaunchService();
        ShopifyProductMetafieldsManager metafields = new ShopifyProductMetafieldsManager();
        ProductMetafieldDTO metaTitle = metafields.getMetafield(false, productToLaunch, ProductMetafieldEnum.META_TITLE);
        ProductMetafieldDTO metaDescription = metafields.getMetafield(false, productToLaunch, ProductMetafieldEnum.META_DESCRIPTION);

        if (service.doesProductMeetRequirementsToLaunch(productToLaunch, metaTitle, metaDescription)){
            ShopifyProductService productService = new ShopifyProductService();
            productService.createShopifyProduct(productToLaunch, metaTitle != null ? metaTitle.getValue() : null, metaDescription != null ? metaDescription.getValue() : null);
            markProductAsLaunched(productToLaunch);
        }

        main(null);

    }

    private static void markProductAsLaunched(ProductDTO productToLaunch) {
        ProductDTO product = new ProductDTO();
        product.setBodyHtml(productToLaunch.getBodyHtml());
        product.setId(productToLaunch.getId());
        product.setTitle("(LANÇADO)" + productToLaunch.getTitle());

        ProductObjectDTO objectDTO= new ProductObjectDTO(product);
        HttpRequestExecutor.updateRequest(Object.class, objectDTO, ShopifyProductService.getUpdateProductRequestUrl(false, product));

    }
}
