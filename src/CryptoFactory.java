public class CryptoFactory {
    //factory to create different type of code/decode objects

    public CaesarCipherTypes getTypeOfCrypto (TypesOfCode enteredCryptoType){
        CaesarCipherTypes type = null;
        return switch (enteredCryptoType) {
            case CODER -> type = new CaesarCoder();
            case DECODER -> type = new CaesarDeCoder();
            case BRUTO_FORCE -> type = new CaesarBrutoForceDeCoder();
            case STATIC_ANALYSIS -> type = new StaticAnalysisDeCoder();
        };
    }

}
