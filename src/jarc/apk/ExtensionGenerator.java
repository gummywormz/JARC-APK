/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jarc.apk;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Makes the extension
 * @author Paul Alves
 */
public class ExtensionGenerator {

    private final int useKey;
    private final String pkg;
    private final String form;
    private final File apkPath;
    private final String orientationSetting;
    //keys for the different apps
    private static final String[] keys = {
            ""
        ,"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh8HMM22e3J95V1TO1RSEF9j4eOATV+pIhGq5FFJek9l1aM9ot0B9rB25pWALNZdiW3hrgRcPR/8YGFaccFbcgneK29hnHbcbwSytdrhK2tF7iV3lbbd/FvENBODZXu3jGruWKRCtlsJgaOmA/97wpHEnn8vqkKLj4+W9kIU+dp+m9GzbB9eQijnt/7XQyJzS1YWsVZyrPaNrEibO33u+CesZ+9lSSJsvocd+r0CK+AvEY8mN27/OhNblDGLOQ/OC8L/AvEm9mU3wk11KyqS18e48XbH9Saxmsl72Z7FTcv4j/C0bqNyeJX1Ja2DMEoabuE2Jxm3vtP9jq+5UjRV1+wIDAQAB"
        ,"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArHskrdG5EmB5QnEBPXw3TuQ1eHtLF2U7tIywykq4Hh8JQkKsX1aNn6azroEtZ0EslCWlvTleP9rBazLDoGYkCktjc/NAXCdoX728k+H/nxulYAWRkxeZiSbuyGAwt6jA67mq/QYHvsTMuIss/nwhdPVTPRrSpXnrWdO3CMMNMZH49edcAfnvrV8qRhJy3h9B8Qak3KYI2P+F501lGc8P6Xf8zzevvcL+ynFj7UgpDVnwDYVbTrnroC1FOpV8oNfnf1nar0Ii2izgDXl4EUt6zfaxEwJtc8o6HDKtPj2VwILOuWphmuMWHKsC+icExHnIm/oF61FMzyARaoWH2PjpJQIDAQAB"
        ,"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq/bF1OTNX8Sqz6ZlYlTJb3S/XRnIxM7Wab4vzSb1oQltJi/YMrcxyYvnQZJRYZDN7AoBBlwNG/dx2yrNdSaNFN1bT3AhzNGa3STOlESF6FktWCHiy3HMkiguALaGGx95PPxSWpBjClHGePbFILwKdWQ75p+j4SiBO1mlNoZgP/F1n+rUVYMOfHKPUbb5zFDH7LbHyZWSAHTJWIZKIcLOcubYq8ITJq5nBFxW7mV0hcLdhflLJCbO/9yemi4Rfs0do7yRyLXuSB1EisBHY00kquIyaVJwJIiBDIKGk3KFhetTX3C1JLWTIuGAmjAsf3LBu7AuflDdia5fOANgPAGnJwIDAQAB"
        ,"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq0UR3Z9iOO5m0taozmsdFdz1oKUCO02VRAMV3ZRwbHdYpg/Mo/dAauTeprLhnGY9N6aCjmDXrPXRcvLc/l8iV+v8U/zi32hvzcXw9d5G5WpQjIhCmLInG5eHOQWGXRTCF1wlrHlv+n4A0ZVYXBJ8zwhLZMrfR3jHhMVpJQOodmU2l/SjIOISfMseNSvkqIibLdzzyuaANh6EfgBOqLzC4yEG9rksTOU3Kr/Pqd/pxA7c1USM4iAZ7lWp8Tnf7m4XOya3K+CrMR0QKvspAF2aknK7eDyLNtG9lTxCDA2T5MD1BkUeHDlmn5ryr2D7myeFTYQqYXAgFrEYh/YKyIe22QIDAQAB"
        };
    /**
     * Creates a new extension generator object
     * @param key Selects which application key to use so the extension can load on a ChromeBook.  0 is none. 1 - 4 for the rest of the slots
     * @param orientation Determines the orientation of the package
     * @param pkgName The name of the package
     * @param pForm The form factor
     * @param apk Path to the APK file
     */
    public ExtensionGenerator(int key, String orientation , String pkgName, String pForm, File apk){
        useKey = key;
        pkg = pkgName;
        form = pForm;
        apkPath = apk;
        orientationSetting = orientation;
    }

    /**
     * Generates the extension folder
     * @param path The path to create the file
     */
    public void generate(File path){
        //gets the resource files.
        InputStream locales = ExtensionGenerator.class.getClassLoader().getResourceAsStream("jarc/res/_locales/en/messages.json");
        InputStream htmlPage = ExtensionGenerator.class.getClassLoader().getResourceAsStream("jarc/res/app_main.html");
        InputStream icon = ExtensionGenerator.class.getClassLoader().getResourceAsStream("jarc/res/icon.png");
        InputStream manifest = ExtensionGenerator.class.getClassLoader().getResourceAsStream("jarc/res/manifest.json");

        String absPath = path.getAbsolutePath(); // get the full path to write
        String sep = "\\"; //path separator
        try{
            //write directories needed
            File baseDir = new File(absPath + sep +  pkg);
            boolean worked = baseDir.mkdirs();
            if(!worked){throw new java.io.IOException();}
            File loc = new File(baseDir.getAbsolutePath() + sep + "_locales" + sep + "en");
            worked = loc.mkdirs();
            if(!worked){throw new java.io.IOException();}
            File vend = new File(baseDir.getAbsolutePath() + sep + "vendor" + sep + "chromium" + sep + "crx");
            worked = vend.mkdirs();
            if(!worked){throw new java.io.IOException();}
            //begin writing the internal resources
            OutputStream out = new FileOutputStream(loc.getAbsolutePath() + sep + "messages.json");
            byte[] buf = new byte[1024];
            int len;
            while((len=locales.read(buf))>0){
                out.write(buf,0,len);
            }
            out = new FileOutputStream(baseDir.getAbsolutePath() + sep + "app_main.html");
            buf = new byte[1024];
            len = 0;
            while((len=htmlPage.read(buf))>0){
                out.write(buf,0,len);
            }

            out = new FileOutputStream(baseDir.getAbsolutePath() + sep + "icon.png");
            buf = new byte[1024];
            len = 0;
            while((len=icon.read(buf))>0){
                out.write(buf,0,len);
            }

            out = new FileOutputStream(baseDir.getAbsolutePath() + sep + "manifest.json");
            buf = new byte[1024];
            len = 0;
            while((len=manifest.read(buf))>0){
                out.write(buf,0,len);
            }

            //now, edit the manifest as needed. first, get the lines and store them in a list
            List<String> lines = Files.readAllLines(Paths.get(baseDir.getAbsolutePath() + sep + "manifest.json"), StandardCharsets.UTF_8);
            String[] rLines = new String[lines.size()];
            //manually convert to array for *much* easier replacing
            for(int i = 0; i < lines.size(); i++){
                rLines[i] = lines.get(i);
            }
            //read the array and replace away!
            for(int j = 0; j < rLines.length; j++){
                String line = rLines[j];
                //replace the apk file name
                if(line.contains("__apkname__")){
                    rLines [j] = line.replace("__apkname__",apkPath.getName());
                }
                //replace name of application pkg where needed
                if(line.contains("__PACKAGE__")){
                    rLines[j] = line.replace("__PACKAGE__",pkg);
                }
                //change form factor if needed
                if(line.contains("__form__")){
                    rLines[j] = line.replace("__form__",form);
                }
                //change orientation if needed
                if(line.contains("__orientation__")){
                    rLines[j] = line.replace("__orientation__",orientationSetting);
                }
                //key stuff for chromebooks
                if(line.contains("__key__")){
                    //if we're building for ARChon, "delete" the line
                    if(useKey == 0){
                        rLines[j] = line.replace("\"key\": \"__key__\",","");
                    }
                    //otherwise add the proper key
                    else{
                        rLines[j] = line.replace("__key__",keys[useKey]);
                    }
                }

            }

            //write back the file
            BufferedWriter w = new BufferedWriter(new FileWriter(baseDir.getAbsolutePath() + sep + "manifest.json"));

            for(String s : rLines){
                w.write(s +"\n");
            }
            //drop the apk to the vendor/chromium/crx folder
            InputStream apkRead = new FileInputStream(apkPath);
            out = new FileOutputStream(new File(baseDir.getAbsolutePath() + sep + "vendor" + sep + "chromium" + sep + "crx" + sep + apkPath.getName()));

            buf = new byte[1024];
            len = 0;
            while((len=apkRead.read(buf))>0){
                out.write(buf,0,len);
            }
            //we're done bro
            out.close();
            apkRead.close();
            w.close();
            locales.close();
            icon.close();
            htmlPage.close();
            manifest.close();

            JARCUI.throwError("Operation Complete!");
        }catch(java.io.IOException e){
            JARCUI.throwError("Could not write some files. Make sure you have proper permissions! (If you are rebuilding a package, please delete the previous folder or save to a new one)");
        }
    }
}