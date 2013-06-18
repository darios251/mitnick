import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import play.*;
import play.db.jpa.Blob;
import play.jobs.*;
import play.test.*;
 
import models.*;
 
@OnApplicationStart
public class Bootstrap extends Job {
 
    public void doJob() {
    	Fixtures.deleteDatabase();
    	Fixtures.loadModels("initial-data.yml");
    }
 
}
