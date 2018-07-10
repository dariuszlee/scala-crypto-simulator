import java.security.KeyPairGenerator;
import java.security.KeyPair;

object KeyGenerator {
  def GenerateKeyPair() : KeyPair = {
    val kpGen = KeyPairGenerator.getInstance("RSA");
    kpGen.initialize(512);
    return kpGen.generateKeyPair();
  }
}
