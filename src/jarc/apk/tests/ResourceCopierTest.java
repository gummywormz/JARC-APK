/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jarc.apk.tests;

import jarc.apk.ExtensionGenerator;
import java.io.File;
import java.util.Scanner;

/**
 * Tests the ExtensionGenerator class' ability to generate the proper stuff
 * @author Paul Alves
 */
public class ResourceCopierTest {

    public static void main(String[] args){
        ExtensionGenerator test = new ExtensionGenerator(1,"landscape","com.me.testpkg","tablet",new File("F:\\butt.apk"));
        Scanner kin = new Scanner(System.in);
        System.out.print("Enter path name: ");
        String path = kin.next();
        test.generate(new File(path));
    }

}
