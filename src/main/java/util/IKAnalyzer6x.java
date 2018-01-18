package util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

public class IKAnalyzer6x extends Analyzer {
	private boolean useSmart;

	public boolean useSmart() {
		return useSmart;
	}

	public void setUseSmart(boolean useSmart) {
		this.useSmart = useSmart;
	}

	public IKAnalyzer6x() {
		this(false); // false表示 使用默认细粒度切分算法
	}

	// 当传入参数为true时，分词器进行智能切分
	public IKAnalyzer6x(boolean useSmart) {
		super();
		this.useSmart = useSmart;
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		Tokenizer _IKTokenizer = new IKTokenizer6x(this.useSmart());
		return new TokenStreamComponents(_IKTokenizer);
	}

}
