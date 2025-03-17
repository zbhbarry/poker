import org.nd4j.linalg.factory.Nd4j;

public class ND4JTest {
    public static void main(String[] args) {
        try {
            System.out.println("ND4J Backend: " + Nd4j.getBackend());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Available backends: " + Nd4j.getBackend().isAvailable());
        System.out.println("Selected backend: " + Nd4j.getBackend());
    }
}
