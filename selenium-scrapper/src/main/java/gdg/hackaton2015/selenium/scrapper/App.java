package gdg.hackaton2015.selenium.scrapper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Oswaldo Ceballos Zavala
 * @author Irving Caro Fierros
 * @author Angel Medina Moreno
 * @author Juan Ku Quintana
 */
public class App {
    private static final String URL = "http://tarifario.taxistascancun.com/Interactive/Index";
    
    public static void main(String[] args) throws IOException {
        
        WebDriver driver = new FirefoxDriver();
        
        driver.manage().window().maximize();
        
        driver.get(URL);
        
        List<WebElement> areas = driver.findElements(new By.ByTagName("area"));
        
        WebElement costEl = driver.findElement(new By.ById("floatText"));
        
        WebElement clearEl = driver.findElement(new By.ById("clear_link"));
        
        //parse coords
        for (WebElement area: areas) {
            String coords = area.getAttribute("coords");
            String[] parts = coords.split(",");
            
            for (int i = 0; i < parts.length; i += 2){
                int x = Integer.parseInt(parts[i]);
                int y = 1240 - Integer.parseInt(parts[i + 1]);
                
                System.out.print(x + " " + y + " ");
            }
            
            System.out.println();
        }
        
        //parse costs
        /*
        for (WebElement originArea: areas){
            for (WebElement destinationArea: areas){
                String originLabel = originArea.getAttribute("alt");
                String destinationLabel = destinationArea.getAttribute("alt");
                
                originArea.click();
                destinationArea.click();
                
                String costText = costEl.getText();
                int cost = Integer.parseInt(costText.split(" ")[1]);
                
                System.out.print(cost + " ");
                
                clearEl.click();
            }
            
            System.out.println();
        }
        */
        
        driver.quit();
    }
}
