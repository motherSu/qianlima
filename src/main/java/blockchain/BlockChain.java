/**
 * @Title555: 
*/

package blockchain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * @Title: 
 * @Description:
 * 区块的结构

首先需要说明一下区块的结构，每个区块包含属性：索引（index），时间戳（timestamp），交易列表（transactions），工作量证明（稍后解释）以及前一个区块的Hash值。

以下是一个区块的结构：

block = {
    'index': 1,
    'timestamp': 1506057125.900785,
    'transactions': [
        {
            'sender': "8527147fe1f5426f9dd545de4b27ee00",
            'recipient': "a77f5cdfa2934df3954a5c7c7da5df1f",
            'amount': 5,
        }
    ],
    'proof': 324984774000,
    'previous_hash': "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824"
}

到这里，区块链的概念就清楚了，每个新的区块都包含上一个区块的Hash，这是关键的一点，它保障了区块链不可变性。如果攻击者破坏了前面的某个区块，那么后面所有区块的Hash都会变得不正确。不理解的话，慢慢消化，可以参考区块链记账原理。
 * @author: 苏腾
 * @date: 2018年3月30日 下午2:26:28
*/
public class BlockChain {
	 // 存储区块链
    private List<Map<String, Object>> chain;
    // 该实例变量用于当前的交易信息列表
    private List<Map<String, Object>> currentTransactions;
    private static BlockChain blockChain = null;

    private BlockChain() {
        // 初始化区块链以及当前的交易信息列表
        chain = new ArrayList<Map<String, Object>>();
        currentTransactions = new ArrayList<Map<String, Object>>();

        // 创建创世区块
        newBlock(100, "0");
    }

    // 创建单例对象
    public static BlockChain getInstance() {
        if (blockChain == null) {
            synchronized (BlockChain.class) {
                if (blockChain == null) {
                    blockChain = new BlockChain();
                }
            }
        }
        return blockChain;
    }

    public List<Map<String, Object>> getChain() {
        return chain;
    }

    public void setChain(List<Map<String, Object>> chain) {
        this.chain = chain;
    }

    public List<Map<String, Object>> getCurrentTransactions() {
        return currentTransactions;
    }

    public void setCurrentTransactions(List<Map<String, Object>> currentTransactions) {
        this.currentTransactions = currentTransactions;
    }

    /**
     * @return 得到区块链中的最后一个区块
     */
    public Map<String, Object> lastBlock() {
        return getChain().get(getChain().size() - 1);
    }

    /**
     * 在区块链上新建一个区块
     * 
     * @param proof
     *            新区块的工作量证明
     * @param previous_hash
     *            上一个区块的hash值
     * @return 返回新建的区块
     */
    public Map<String, Object> newBlock(long proof, String previous_hash) {

        Map<String, Object> block = new HashMap<String, Object>();
        block.put("index", getChain().size() + 1);
        block.put("timestamp", System.currentTimeMillis());
        block.put("transactions", getCurrentTransactions());
        block.put("proof", proof);
        // 如果没有传递上一个区块的hash就计算出区块链中最后一个区块的hash
        block.put("previous_hash", previous_hash != null ? previous_hash : hash(getChain().get(getChain().size() - 1)));

        // 重置当前的交易信息列表
        setCurrentTransactions(new ArrayList<Map<String, Object>>());

        getChain().add(block);

        return block;
    }

    /**
     * 生成新交易信息，信息将加入到下一个待挖的区块中
     * 
     * @param sender
     *            发送方的地址
     * @param recipient
     *            接收方的地址
     * @param amount
     *            交易数量
     * @return 返回该交易事务的块的索引
     */
    public int newTransactions(String sender, String recipient, long amount) {

        Map<String, Object> transaction = new HashMap<String, Object>();
        transaction.put("sender", sender);
        transaction.put("recipient", recipient);
        transaction.put("amount", amount);

        getCurrentTransactions().add(transaction);

        return (Integer) lastBlock().get("index") + 1;
    }

    /**
     * 生成区块的 SHA-256格式的 hash值
     * 
     * @param block
     *            区块
     * @return 返回该区块的hash
     */
    public static Object hash(Map<String, Object> block) {
        return new Encrypt().getSHA256(new JSONObject(block).toString());
    }
    
    /**
     * 简单的工作量证明: 
     *   - 查找一个 p' 使得 hash(pp') 以4个0开头 
     *   - p 是上一个块的证明, p' 是当前的证明
     *   
     * @param last_proof
     *               上一个块的证明
     * @return
     */
    public long proofOfWork(long last_proof) {
        long proof = 0;
        while (!validProof(last_proof, proof)) {
            proof += 1;
        }
        return proof;
    }

    /**
     * 验证证明: 是否hash(last_proof, proof)以4个0开头?
     * 
     * @param last_proof
     *            上一个块的证明
     * @param proof
     *            当前的证明
     * @return 以4个0开头返回true，否则返回false
     */
    public boolean validProof(long last_proof, long proof) {
        String guess = last_proof + "" + proof;
        String guess_hash = new Encrypt().getSHA256(guess);
        return guess_hash.startsWith("0000");
    }
}

