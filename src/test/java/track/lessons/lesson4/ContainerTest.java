package track.lessons.lesson4;

import org.junit.Assert;
import org.junit.Test;
import track.container.Container;
import track.container.JsonConfigReader;
import track.container.beans.Car;
import track.container.beans.Engine;
import track.container.beans.Gear;
import track.container.config.Bean;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;

import java.io.File;
import java.util.List;

public class ContainerTest {

    @Test
    public void mySimpleTestById() throws ReflectiveOperationException, InvalidConfigurationException {
        ConfigReader reader = new JsonConfigReader();
        List<Bean> beans = reader.parseBeans(new File("src/main/resources/config.json"));
        Container container = new Container(beans);
        Car car = null;
        car = (Car) container.getById("carBean");
        compareFields(car);
    }

    @Test
    public void mySimpleTestByName() throws ReflectiveOperationException, InvalidConfigurationException {
        ConfigReader reader = new JsonConfigReader();
        List<Bean> beans = reader.parseBeans(new File("src/main/resources/config.json"));
        Container container = new Container(beans);
        Car car = null;
        car = (Car) container.getByClass("track.container.beans.Car");
        compareFields(car);
    }

    private void compareFields(Car car) {
        Gear gear = car.getGear();
        Engine engine = car.getEngine();
        Assert.assertEquals(6, gear.getCount());
        Assert.assertEquals(200, engine.getPower());
    }
}
