package bomberman.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import bomberman.core.BomberMan;

public class BomberManActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new BomberMan());
  }
}
