import java.time.Duration;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;





public class OpenVeeamCareersPage {
    public static void main(String[] args) {
        int expectedJobs  = 0;
        String department  = new String();
        String country  = new String();
        String state  = new String();
        String city  = new String();

        openVeeamCareersPage(expectedJobs, department, country, state, city);
    }

    public static void openVeeamCareersPage(int expectedJobs, String department, String country, String state, String city) {
        Scanner scanner = new Scanner(System.in);
        List<WebElement> cityTogglers;

        // Set the path for the Firefox driver
        System.setProperty("webdriver.chrome.driver", "C:\\selenium webdriver\\chrome driver\\geckodriver.exe");

        // Create FirefoxOptions object
        FirefoxOptions options = new FirefoxOptions();

        // Create a new instance of the FirefoxDriver
        WebDriver driver = new FirefoxDriver(options);

        // Prompt the user to insert details
        System.out.println("Please insert the department:");
        department = scanner.nextLine();
        System.out.println("Please insert the country:");
        country = scanner.nextLine();
        System.out.println("Please insert the state, in case not applicable please press enter:");
        state = scanner.nextLine();
        System.out.println("Please insert the city:");
        city = scanner.nextLine();
        System.out.println("Please insert the number of expected jobs:");
        expectedJobs = scanner.nextInt();
        scanner.close();

        // Navigate to the specified URL and maximize the browser window
        driver.get("https://careers.veeam.com/vacancies");
        driver.manage().window().maximize();

        // Select department and country
        selectDepartment(driver, department);
        selectCountry(driver, country);

        // Find cityTogglers
        cityTogglers = driver.findElements(By.id("city-toggler"));

        // Select state and city based on the size of cityTogglers list
        if (cityTogglers.size() == 2) {
            selectDropDown(driver, city, cityTogglers.get(1));
        } else {
            selectDropDown(driver, state, cityTogglers.get(1));
            selectDropDown(driver, city, cityTogglers.get(2));
        }

        // Click on Find A Career button
        clickFindACareer(driver);

        if (howManyJobs(driver) == expectedJobs) {
            System.out.println("Test succeeded.");
        } else {
            System.out.println("Test failed.");
        }
        
        driver.quit();

    }

    public static void clickFindACareer(WebDriver driver) {
        // Click on the Find A Career button
        WebElement findCareerButton = driver.findElement(By.xpath("//button[@class='btn btn-outline-success']"));
        findCareerButton.click();
    }

    public static int howManyJobs(WebDriver driver) {
        // Find all job elements
        List<WebElement> elements = driver.findElements(By.xpath("//a[contains(@class, 'card') and contains(@class, 'card-md-45') and contains(@class, 'card-no-hover') and contains(@class, 'card--shadowed')]"));
        
        // Count the number of visible job elements
        int visibleCount = 0;
        for (WebElement link : elements) {
            if (link.isDisplayed()) {
                visibleCount++;
            }
        }
        return visibleCount;
    }
    
    
    /**
     * Selects a department from the dropdown menu.
     *
     * @param driver     The WebDriver instance to use.
     * @param department The department to select from the dropdown menu.
     */
    public static void selectDepartment(WebDriver driver, String department) {
        // Click on the department-toggler button to open the dropdown menu
        WebElement departmentToggler = driver.findElement(By.id("department-toggler"));
        departmentToggler.click();

        // Wait for the dropdown menu to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dropdown-menu")));

        // Locate the department option and click on it
        WebElement departmentOption = dropdownMenu.findElement(By.xpath(".//a[@role='button' and text()='" + department + "']"));
        departmentOption.click();

        // Wait for the dropdown menu to be invisible
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("dropdown-menu")));
    }

    /**
     * Selects a country from the dropdown menu.
     *
     * @param driver  The WebDriver instance to use.
     * @param country The country to select from the dropdown menu.
     */
    public static void selectCountry(WebDriver driver, String country) {
        // Click on the city-toggler button to open the dropdown menu
        WebElement countryToggler = driver.findElement(By.id("city-toggler"));
        countryToggler.click();

        // Wait for the dropdown menu to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@id='city-toggler'])/following-sibling::div[contains(@class, 'dropdown-menu')]")));

        // Locate the country option and click on it
        WebElement countryOption = dropdownMenu.findElement(By.xpath(".//a[@role='button' and text()='" + country + "']"));
        countryOption.click();

        // Wait for the dropdown menu to be invisible
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("dropdown-menu")));
    }

    /**
     * Selects an option from a dropdown menu.
     *
     * @param driver  The WebDriver instance to use.
     * @param text    The text of the option to select.
     * @param element The WebElement representing the dropdown element.
     */
    public static void selectDropDown(WebDriver driver, String text, WebElement element) {
        // Click on the element to open the dropdown menu
        element.click();

        // Wait for the dropdown menu to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@id='city-toggler']/following-sibling::div[contains(@class, 'dropdown-menu')])[last()]")));

        // Locate the dropdown option based on the given text and click on it
        WebElement dropDownMenuOption = dropdownMenu.findElement(By.xpath(".//a[@role='button' and text()='" + text + "']"));
        dropDownMenuOption.click();

        // Wait for the dropdown menu to be invisible
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("dropdown-menu")));
    }

}