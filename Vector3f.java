
public class Vector3f {
    public float x, y, z;

    // Constructor
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Vector addition
    public Vector3f add(Vector3f other) {
        return new Vector3f(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    // Vector subtraction
    public Vector3f subtract(Vector3f other) {
        return new Vector3f(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    // Scalar multiplication
    public Vector3f multiply(float scalar) {
        return new Vector3f(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    // Dot product
    public float dot(Vector3f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    // Cross product
    public Vector3f cross(Vector3f other) {
        return new Vector3f(
            this.y * other.z - this.z * other.y,
            this.z * other.x - this.x * other.z,
            this.x * other.y - this.y * other.x
        );
    }

    // Vector normalization (unit vector)
    public Vector3f normalize() {
        float length = (float) Math.sqrt(x * x + y * y + z * z);
        return new Vector3f(x / length, y / length, z / length);
    }

    // To String
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}

