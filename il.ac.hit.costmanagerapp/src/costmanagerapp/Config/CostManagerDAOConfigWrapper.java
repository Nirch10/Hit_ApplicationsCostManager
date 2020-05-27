package costmanagerapp.Config;

import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
import com.sun.xml.internal.txw2.output.XmlSerializer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CostManagerDAOConfigWrapper {

    public static CostManagerDAOConfig costManagerDAOConfig;

    public static void Serialize(CostManagerDAOConfig inCostManagerDAOConfig, String filePath) throws IOException {

    }

    public static CostManagerDAOConfig Deserialize(String configPath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(configPath)));
        return costManagerDAOConfig;
    }
}

