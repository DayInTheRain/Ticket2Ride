
class Matrix3 {
    double[] values;
    Matrix3(double[] values) {
        this.values = values;
    }
    Matrix3 multiply(Matrix3 other) {
        double[] result = new double[9];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                for (int i = 0; i < 3; i++) {
                    result[row * 3 + col] +=
                        this.values[row * 3 + i] * other.values[i * 3 + col];
                }
            }
        }
        return new Matrix3(result);
    }
    Vertex transform(Vertex in) {
        return new Vertex(
            in.x * values[0] + in.y * values[3] + in.z * values[6],
            in.x * values[1] + in.y * values[4] + in.z * values[7],
            in.x * values[2] + in.y * values[5] + in.z * values[8]
        );
    }
    

    public Matrix3 inverse() {
        double a = values[0], b = values[1], c = values[2];
        double d = values[3], e = values[4], f = values[5];
        double g = values[6], h = values[7], i = values[8];

        double determinant = a * (e * i - f * h) - 
                             b * (d * i - f * g) + 
                             c * (d * h - e * g);

        if (determinant == 0) {
            throw new ArithmeticException("Matrix is not invertible");
        }

        double invDet = 1.0 / determinant;

        return new Matrix3(new double[]{
            (e * i - f * h) * invDet, (c * h - b * i) * invDet, (b * f - c * e) * invDet,
            (f * g - d * i) * invDet, (a * i - c * g) * invDet, (c * d - a * f) * invDet,
            (d * h - e * g) * invDet, (b * g - a * h) * invDet, (a * e - b * d) * invDet
        });
    }
}