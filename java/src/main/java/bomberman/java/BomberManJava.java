package bomberman.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import bomberman.core.BomberMan;

public class BomberManJava {

  public static void main(String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    config.width = 608;
    config.height = 544;
    // use config to customize the Java platform, if needed
    JavaPlatform.register(config);
    PlayN.run(new BomberMan());
  }
}
