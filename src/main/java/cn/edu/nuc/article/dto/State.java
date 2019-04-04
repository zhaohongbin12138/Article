package cn.edu.nuc.article.dto;

import java.io.Serializable;

/**
 * 树的状态
 * @author 王凯
 *
 */
public class State implements Serializable {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 5556273520036525736L;
	
	/**
	 * 树的节点是否被选中
	 */
	private boolean checked;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
}
