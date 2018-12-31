import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Main {

    public static void main(String[] args ) {
        //火狐浏览器的驱动的地址
        //https://github.com/mozilla/geckodriver/releases
//        System.setProperty("webdriver.chrome.driver","/home/wxq/devel/chromeDriver/chromedriver");
        System.setProperty("webdriver.gecko.driver","/home/wxq/devel/firefoxDriver/geckodriver");
        WebDriver driver = new FirefoxDriver();
        //获取navigation对象,用于打开网页,
        Navigation navigation = driver.navigate();
        //使用to()跳到指定的页面
        navigation.to("http://www.baidu.com");
        //返回上一頁
//        navigation.back();
//        navigation.forward();
        //刷新页面
//        navigation.refresh();
        //获取当前的标题
        String title = driver.getTitle();
        System.out.println( title );
        //获取当前的url
        String url = driver.getCurrentUrl();
        System.out.println( url );

        //创建一个页面元素,并用通过id找到这个元素,,By.id\
        WebElement input = driver.findElement(By.id("kw"));
        input.sendKeys("找到文本框");
//        System.out.println( "到这里了");
        driver.close();
    }
}
