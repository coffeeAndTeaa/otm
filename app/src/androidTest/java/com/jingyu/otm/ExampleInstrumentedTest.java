package com.jingyu.otm;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.jingyu.otm.databinding.ActivityLoginBinding;
import com.jingyu.otm.db.Run;
import com.jingyu.otm.db.RunDao;
import com.jingyu.otm.db.RunDataBase;

import com.jingyu.otm.db.User;
import com.jingyu.otm.db.UserDao;
import com.jingyu.otm.repository.LoginRepository;
import com.jingyu.otm.repository.RunRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.jingyu.otm", appContext.getPackageName());
    }


    @Test
    public void validLogin() throws ExecutionException, InterruptedException {
        String name = "Bruce";
        String password = "123456";

        LoginRepository repo = new LoginRepository();

        User user = repo.login(name, password);
        assertNotNull(user);
    }

    @Test
    public void inValidLogin() throws ExecutionException, InterruptedException {
        String name = "Bruce";
        String password = "1234";

        LoginRepository repo = new LoginRepository();

        User user = repo.login(name, password);
        assertNull(user);
    }

    @Test
    public void recentlyRegisteredLogin() throws ExecutionException, InterruptedException {
        String name = "Jack";
        String password = "1234";

        LoginRepository repo = new LoginRepository();

        User user = repo.login(name, password);
        assertNotNull(user);
        assertEquals(user.name, "Jack");
        assertEquals(user.weight, 233.0, .1);
        assertEquals(user.age, 123, .1);
    }

    @Test
    public void newRegistration() throws ExecutionException, InterruptedException {
        // Create a new user
        String name = "Candan";
        String password = "abc";
        User userInput = new User(name, 3.12, 192, 987.123, password);

        // Login to the reposiory and get the database information
        LoginRepository repo = new LoginRepository();
        RunDataBase dataBase;
        dataBase = Room.databaseBuilder(getApplicationContext(), RunDataBase.class, "my_database" ).build();

        // Insert the previously made user into our database
        UserDao userDao = dataBase.userDao();
        userDao.insertUser(userInput);

        // Attempt to fetch that user from the database
        User userOut = repo.login(name, password);

        // Check if that user is valid
        assertNotNull(userOut);
        assertEquals(userOut.name, "Candan");
        assertEquals(userOut.weight, 987.123, .0001);
        assertEquals(userOut.age, 192, .1);
    }

    @Test
    public void testBMI() throws ExecutionException, InterruptedException {
        String name = "Jack";
        String password = "1234";

        LoginRepository repo = new LoginRepository();

        // TODO: Check to make sure this should be a RunRepository function and not LoginRepository
        RunRepository runRepo = new RunRepository();

        // Fetch the user from the repository, then check the BMI
        User user = repo.login(name, password);
        Double BMI;
        BMI = runRepo.getTheBmiForUser(user);

        // Calculate the expected BMI, then compare the values we're getting
        double expectedBMI = (user.weight / (user.height * user.height));
        assertEquals(BMI, expectedBMI, .000001);
        assertEquals(user.weight, 233, .00001);
        assertEquals(user.height, 6, .00001);



    }


    @Test
    public void registerExistingUser() throws ExecutionException, InterruptedException {
        // Create a new user
        String name = "Bruce";
        String password = "abc";
        User userInput = new User(name, 3.12, 192, 987.123, password);

        // Login to the reposiory and get the database information
        LoginRepository repo = new LoginRepository();
        RunDataBase dataBase;
        dataBase = Room.databaseBuilder(getApplicationContext(), RunDataBase.class, "my_database" ).build();

        // Insert the previously made user into our database
        UserDao userDao = dataBase.userDao();
        userDao.insertUser(userInput); // This should fail

        // TODO: ensure that this test fails later - it is currently passing.
        /*
        // Attempt to fetch that user from the database
        User userOut = repo.login(name, password);

        // Check if that user is valid
        assertNotNull(userOut);
        assertEquals(userOut.name, "Candan");
        assertEquals(userOut.weight, 987.123, .0001);
        assertEquals(userOut.age, 192, .1);
        */
    }


    @Test
    public void ChangedInformation() throws ExecutionException, InterruptedException {
        String name = "Jack";
        String password = "1234";

        LoginRepository repo = new LoginRepository();

        User user = repo.login(name, password);
        // TODO: Finish implementing this later after we make changes.
        /*
        repo.update();

        assertNotNull(user);
        assertEquals(user.name, "Jack");
        assertEquals(user.weight, 233.0, .1);
        assertEquals(user.age, 123, .1);

         */
    }

    @Test
    public void preexistRun() throws ExecutionException, InterruptedException {
        String name = "Bruce";
        String password = "123456";

        LoginRepository repo = new LoginRepository();
        RunRepository runs = new RunRepository();

        // Login to Bruce's account
        User user = repo.login(name, password);
        assertNotNull(user);
        System.out.println("Succesfull login to: " + user.name);
        //Run r1 = new Run(Bruce.id_user, "Bruce First run", 10, 100);

        // Manually insert a run
        Run firstRun = new Run(user.id_user, "Bruce unit test", 1000, 333);
        System.out.println("Created a run");
        runs.insertRun(firstRun);
        System.out.println("Inserted a run");

        // Attempt to get the list of runs for the user
        List<Run> runList = runs.getAllRunsForUser(user).getValue();
        if (runList == null) {
            System.out.println(" Run list is empty");
        } else {
            Run r1 = runList.get(0);
            //System.out.println("First run got");
            if(r1 != null) {
                System.out.println("On his run Bruce went : " + r1.steps + " steps.");
            }
        }

       // System.out.println("Run name: " + firstRun.runName);
       // System.out.println("Run seconds: " + firstRun.seconds);
       // System.out.println("Run steps: " + firstRun.steps);
        // Check the steps for each run
        // LiveData<List<Run>> getAllRunsForUser(User user)

    }
}
