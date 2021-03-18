public class Wrappers {
    public static class IntegerWrapper {
        private Integer value;
        public IntegerWrapper(Integer value) {
            this.value = value;
        }
        public void setValue(Integer value) {
            this.value = value;
        }
        public Integer getValue() {
            return value;
        }
        public String toString() {
            return String.valueOf(value);
        }
    }

    public static class BooleanWrapper {
        private Boolean value;
        public BooleanWrapper(Boolean value) {
            this.value = value;
        }
        public void setValue(Boolean value) {
            this.value = value;
        }
        public Boolean getValue() {
            return value;
        }
        public String toString() {
            return String.valueOf(value);
        }
    }
}