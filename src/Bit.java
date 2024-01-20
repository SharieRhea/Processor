public class Bit {
    private boolean value;

    public Bit(Boolean value) {
        set(value);
    }

    public void set(boolean value) {
        this.value = value;
    }

    public void toggle() {
        value = (value) ? false : true;
    }

    public void set() {
        value = true;
    }

    public void clear() {
        value = false;
    }

    public Boolean getValue() {
        return value;
    }

    public Bit and(Bit other) {
        if (value == false)
            return new Bit(false);
        else if (other.getValue() == false)
            return new Bit(false);
        return new Bit(true);
    }

    public Bit or(Bit other) {
        if (value)
            return new Bit(true);
        if (other.getValue())
            return new Bit(true);
        return new Bit(false);
    }

    public Bit xor(Bit other) {
        if (value) {
            if (other.getValue())
                return new Bit(false);
            return new Bit(true);
        }

        if (other.getValue())
            return new Bit(true);
        return new Bit(false);
    }

    public Bit not() {
        return (value) ? new Bit(false) : new Bit(true);
    }

    @Override
    public String toString() {
        return (value) ? "t" : "f";
    }
}
