package ds.Ch02_tree.$1.bst;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @description 二分搜索树
 * @author Daffupman
 * @date 2019-03-08 19:55
 */
public class BST<E extends Comparable<E>> {

	//BST的节点
	private class Node {
		private E e;
		private Node left, right;
		
		public Node(E e) {
			this.e = e;
			left = right = null;
		}
		
		@Override
		public String toString() {
			return e.toString();
		}
	}
	
	private Node root;
	private int size;
	
	public BST() {
		root = null;
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	//--------增--------
	//添加不重复的元素e
	public void add(E e) {
		root = add(root, e);
	}

	//向以node为根的二分搜索树插入元素e，递归算法
	private Node add(Node node, E e) {
		if(node == null) {
			// 找到目标位置
			size ++;
			// 生成新节点，并返回回去
			return new Node(e);
		}
		if(e.compareTo(node.e) < 0) {
			// 目标元素比当前节点值小，去往左子树
			node.left = add(node.left, e);
		} else if(e.compareTo(node.e) > 0) {
			// 目标元素比当前节点值大，去往右子树
			node.right = add(node.right, e);
		}
		// 返回以该node为根的树，该树的左右子树已更新
		return node;
	}

	public void addNR(E e) {
		if(root == null) {
			// 空树的情况
			root = new Node(e);
			size ++;
			return;
		}

		// 设立双指针，prev紧跟在curr后面
		Node prev = root;
		Node curr = root;

		while(curr != null) {
			prev = curr;
			// curr指向null才停止
			if(e.compareTo(curr.e) < 0) {
				curr = curr.left;
			} else if(e.compareTo(curr.e) > 0) {
				curr = curr.right;
			} else {
				// BST中已存在该元素
				return;
			}
		}

		// 判断插入的位置
		if(e.compareTo(prev.e) < 0) {
			prev.left = new Node(e);
		} else {
			prev.right = new Node(e);
		}
	}
	
	//--------删--------
	public void remove(E e) {
		if(root == null) {
			throw new RuntimeException("Empty BST！");
		} else {
			root = remove(root, e);
		}
	}
	
	private Node remove(Node node, E e) {
		if(node == null) {
			// 未找到目标元素
			return null;
		}
		if(e.compareTo(node.e) < 0) {
			// 目标元素比当前节点值小，去往左子树
			node.left = remove(node.left, e);
			return node;
		} else if(e.compareTo(node.e) > 0) {
			// 目标元素比当前节点值大，去往右子树
			node.right = remove(node.right, e);
			return node;
		} else {
			// 找到目标元素
			if(node.right == null) {
				// 目标元素只有左子树
				// 保存右子树（也可能为null），并返回上去
				Node leftNode = node.left;
				node.left = null;
				size --;
				return leftNode;
			} else if(node.left == null) {
				// 目标元素只有右子树
				// 保存右子树（也可能为null），并返回上去
				Node rightNode = node.right;
				node.right = null;
				size --;
				return rightNode;
			} else {
				// 目标元素的左右子树均存在
				// 获取以当前node为根的树中的最小值节点,记为继承节点：successor
				Node successor = minimum(node.right);
				// successor的右子树为移除自己（即最小值节点）后的树
				successor.right = removeMin(node.right);
				// successor的左子树为原本节点的左子树
				successor.left = node.left;
				// 将目标节点与树脱离
				node.left = node.right = null;
				// 返回继承节点
				return successor;
			}
		}
	}

	// 移除以node为根的树中的最小值节点
	private Node removeMin(Node node) {
		if(node.left == null) {
			// 当前节点为最小值节点
			// 保存其右子树并返回上去
			Node rightNode = node.right;
			node.right = null;
			size --;
			return rightNode;
		}
		return removeMin(node.left);
	}

	//--------查--------
	public E minimum() {
		if(root == null) {
			throw new RuntimeException("Empty BST！");
		} else {
			return minimum(root).e;
		}
	}
	
	private Node minimum(Node node) {
		if(node.left == null)	return node;
		return minimum(node.left);
	}
	
	public E maximum() {
		if(root == null) {
			throw new RuntimeException("Empty BST！");
		} else {
			return maximum(root).e;
		}
	}
	
	private Node maximum(Node node) {
		if(node.right == null)	return node;
		return minimum(node.right);
	}
	
	//返回元素是否在该树里
	public boolean contains(E e) {
		return contains(root, e);
	}
	
	//树中是否包含元素e，递归算法
	private boolean contains(Node node, E e) {
		if(node == null)	return false;
		if(e.compareTo(node.e) < 0) {
			return contains(node.left, e);
		} else if(e.compareTo(node.e) > 0) {
			return contains(node.right, e);
		} else {
			return true;
		}
	}
	
	//--------遍历--------
	//深度优先遍历
	//先根遍历
	public void preOrder() {
		if(root == null) {
			System.out.println("Empty BST!");
		} else {
			preOrder(root);
		}
	}
	
	//先根遍历(递归)
	private void preOrder(Node node) {
		if(node == null) {
			return;
		}
		System.out.print(node.e+" ");
		preOrder(node.left);
		preOrder(node.right);
	}
	
	//先根遍历(非递归)
	public void preOrderNR() {
		Stack<Node> stack = new Stack<>();
		stack.add(root);
		while(!stack.isEmpty()) {
			Node curr = stack.pop();
			System.out.print(curr.e+" ");
			
			if(curr.right != null)	stack.push(curr.right);
			if(curr.left != null)	stack.push(curr.left);
		}
	}
	
	//中根遍历
	public void inOrder() {
		if(root == null) {
			System.out.println("Empty BST!");
		} else {
			inOrder(root);
		}
	}
	
	//中根遍历（递归）
	private void inOrder(Node node) {
		if(node == null)	return;
		
		inOrder(node.left);
		System.out.print(node.e+" ");
		inOrder(node.right);
	}
	
	//中根遍历（递归）
	public void inOrderNR() {
		Stack<Node> stack = new Stack<>();
		Node curr = root;
		while(curr != null || !stack.isEmpty()) {
			if(curr != null) {
				stack.push(curr);
				curr = curr.left;
			} else {
				curr = stack.pop();
				System.out.print(curr.e+" ");
				curr = curr.right;
			}
		}
	}
	
	//后根遍历
	public void postOrder() {
		if(root == null) {
			System.out.println("Empty BST!");
		} else {
			postOrder(root);
		}
	}
	
	//后根遍历（递归）
	private void postOrder(Node node) {
		if(node == null)	return;

		postOrder(node.left);
		postOrder(node.right);
		System.out.print(node.e+" ");
	}
	
	//后根遍历（非递归）
	public void postOrderNR() {
		Stack<Node> traStack = new Stack<>();
		Stack<Node> resStack = new Stack<>();
		traStack.push(root);
		while(!traStack.isEmpty()) {
			Node curr = traStack.pop();
			resStack.push(curr);
			
			if(curr.left != null)	traStack.push(curr.left);
			if(curr.right != null)	traStack.push(curr.right);
		}
		while(!resStack.isEmpty()) {
			System.out.print(resStack.pop()+" ");
		}
	}
	
	//广度优先遍历，借助queue
	public void levelOrder() {
		Queue<Node> queue = new LinkedList<>();
		queue.add(root);
		System.out.println("-------层序遍历-------");
		while(!queue.isEmpty()) {
			Node curr = queue.remove();
			System.out.print(curr.e+" ");
			
			if(curr.left != null)	queue.add(curr.left);
			if(curr.right != null)	queue.add(curr.right);
		}
		System.out.println("\n--------------------");
	} 
	
	public static void main(String[] args) {
		BST<Integer> bst = new BST<>();
		bst.add(2);
		for (int i = 0; i < 5; i++) {
			bst.add(i);
		}
		bst.preOrder();
		bst.inOrder();
		bst.postOrder();
		bst.levelOrder();
		
	}
}
