import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public final class HuffMan {

    // private members =====================================

    private final class Node implements Comparable<Node> {
        private String data = "";
        private int freq = 0;
        private Node left = null;
        private Node right = null;

        Node() {

        }

        public Node(String data, int freq) {
            this.data = data;
            this.freq = freq;
        }

        @Override
        public int compareTo(Node o) {
            return this.freq - o.freq;
        }

        public String toString() {
            return (this.data + " : " + this.freq);
        }
    }

    private String str = "";
    private String encoding = "";
    private String decoding = "";
    private HashMap<String, String> encodeMap = new HashMap<>();
    private HashMap<String, String> decodeMap = new HashMap<>();
    private ArrayList<Node> nodes = new ArrayList<>();

    private void encrypt(Node root, String code) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.left == null) {
            encodeMap.put(root.data, code);
            decodeMap.put(code, root.data);
        }

        
        encrypt(root.left, code + 0);
        encrypt(root.right, code + 1);
    }

    private void encrypt() {
        if (str.length() == 0) {
            System.err.println("Empty String can't be encryptd!");
            return;
        }

        int[] freqMap = new int[256];
        for (int i = 0; i < str.length(); i++) {
            freqMap[str.charAt(i)]++;
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (int i = 0; i < 256; i++) {
            if (freqMap[i] != 0) {
                Node node = new Node("" + ((char) i), freqMap[i]);
                pq.add(node);
                nodes.add(node);
            }
        }

        while (pq.size() > 1) {
            Node first = pq.remove();
            Node second = pq.remove();

            Node node = new Node(first.data + second.data, first.freq + second.freq);
            node.left = first;
            node.right = second;

            pq.add(node);
        }

        encrypt(pq.remove(), "0");

    }

    private void encode() {
        encrypt();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            sb.append(encodeMap.get("" + str.charAt(i)));
        }

        encoding = sb.toString();
    }

    private void decode() {
        if (encoding.length() == 0) {
            System.err.println("Empty message can't be decoded!");
            return;
        }

        StringBuilder dec = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < encoding.length(); i++) {
            dec.append(encoding.charAt(i));
            if (decodeMap.containsKey(dec.toString())) {
                sb.append(decodeMap.get(dec.toString()));
                dec.delete(0, dec.length());
            }
        }

        decoding = sb.toString();
    }

    // public members =====================================

    HuffMan() {

    }

    HuffMan(String str) {
        this.str = str;
    }

    void setString(String str) {
        this.str = str;
    }

    HashMap<String, String> getEncodeMap() {
        return encodeMap;
    }

    HashMap<String, String> getDecodeMap() {
        return decodeMap;
    }

    void encodeString() {
        encode();
    }

    void decodeString() {
        decode();
    }

    String getEncoding() {
        return encoding;
    }

    String getDecoding() {
        return decoding;
    }

    void getFreqMap() {
        System.out.println(nodes);
    }
}