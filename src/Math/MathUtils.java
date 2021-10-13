package Math;

public class MathUtils {
    public static double getGeometricMean(double[] data) {
        double composition = 1;
        for (int i = 0; i < data.length; i++) {
            composition *= data[i];
        }
        double pow = Math.pow(composition, (double) 1 / data.length);
        return pow;
    }

    public double multiplyVectorByVector(double[] vector1, double[] vector2){
        double result = 0;

        for (int i = 0;i<vector1.length;i++){
            result += vector1[i]*vector2[i];
        }

        return result;
    }
}
