package SS14;

public class Pkt {
    int seqnum;
    int acknum;
    int checksum;
    byte[] payload = new byte[20];
}
