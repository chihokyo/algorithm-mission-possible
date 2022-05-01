public interface UF {
    // 获取长度
    int getSize();

    // 检查2个元素是否相连
    boolean isConnected(int p, int q);

    // 合并2个元素
    void unionElements(int p, int q);
}
