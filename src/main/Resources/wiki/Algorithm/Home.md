#Algorithms, 4th Edition
<http://algs4.cs.princeton.edu/home/>

本页内容包括：

* 数据结构  
2. 排序
 * 选择排序
 2. 插入排序
 3. 希尔排序
 4. 归并排序
 5. 快速排序
 6. 优先队列
3. 查找
4. 图
5. 字符串

此wiki是《算法 第四版》的读书笔记。摘录各类数据结构及其算法。

#数据结构
---
###背包（Bag）
背包是一种不支持从中删除元素的集合数据类型——它的目的就是帮助用例收集元素并迭代遍历所有收集到的元素（用例也可以检查背包是否为空或者获取背包中元素的数量）。

>API



    public interface Bag {
        void    add(Item item);
        boolean isEmpty();
        int     size();
    }
	


###先进先出（FIFO）队列（Queue)
先进先出队列（或简称队列）是一种基于先进先出（FIFO）策略的集合类型。
>API

```java
	public Interface Queue{
		void    enqueue(Item item);
		Item    dequeue();
		boolean isEmpty();
		int     size();
}

```

###下压（后进先出，LIFO）栈（Stack）
下压栈（或简称栈）是一种基于后进先出（LIFO）策略的集合类型。
*用例：算术表达式求值*
>API

```java
	public Interface Stack{
		void    push(Item item);
		Item    pop();
		boolean isEmpty();
		int     size();
}
```

>**Stack**和**Queue**的API的唯一不同之处只是他们的名称和方法名。这让我们认识到无法简单地通过一列方法的签名说明一个数据类型的所有特点。在这里只有自然语言的描述才能说明选择被删除元素（或是在foreach语句中下一个被处理的元素）的规则。这些规则的差异是API的重要组成部分，而且显然对用例的开发十分的重要。

>重要：**实现一份API的第一步就是选择数据的表示方式**


###链表
定义。链表是一种递归的数据结构，他或者为空（null）或者是指向一个结点（node）的引用，该结点含有一个泛型的元素和一个指向另一条链表的引用。
```java
class Node
{
	Item item;
	Node next;
}
```

链表表示的是一列元素。

* 在表头插入结点
* 从表头删除节点
* 在表尾插入结点
* 其他位置的插入和删除操作


###优先队列






#排序
---
排序算法类的模板
```java
public class Example
{
	public static void sort(Comparable[]a){}
	private static boolean less(Comparable v,Comparable w)
	{
		return v.compareTo(w)<0;
	}
	private static void exch(Comparable[] a,int i,int j)
	{
		Comparable t=a[i];
		a[i]=a[j];
		a[j]=t;
	}
	private static void show(Comparable[] a)
	{
		for(int i=0;i<a.length;i++)
			StdOut.print(a[i]+" ");
		StdOut.println();
	}
	public static boolean isSorted(Comparable[] a)
	{
		for(int i=1;i<a.length;i++)
			if(less(a[i],a[i-1])) return false;
		return true;
	}
	public static void main(String[] args)
	{
		String[] a=In.readStrings();
		sort(a);
		assert isSorted(a);
		show(a);
	}
}
```
###选择排序
```java
public class Selection
{
	public static void sort(Comparable[] a)
	{
		//将a[]按升序排列
		int N=a.length;
		for(int i=0;i<N;i++)
		{
			int min=i;
			for(int j=i+1;j<N;j++)
				if(less(a[j],a[min])) min=j;
			exch(a,i,min);
		}
	}
	//...
}
```
###插入排序
```java
public class Insertion
{
	public static void sort(Comparable[] a)
	{
		//将a[]按升序排列
		int N=a.length;
		for(int i=1;i<N;i++)
		{
			for(int j=i;j>0&&less(a[j],a[j-1]);j--)
				exch(a,j,j-1);
		}
	}
	//...
}
```
###冒泡排序
重复地走访过要排序的数列，一次比较两个元素，如果他们的顺序错误就把他们交换过来。走访数列的工作是重复地进行直到没有再需要交换，也就是说该数列已经排序完成。
冒泡排序算法的运作如下：
1. 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
2. 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。这步做完后，最后的元素会是最大的数。
3. 针对所有的元素重复以上的步骤，除了最后一个。
4. 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。


```java
public class Bubble
{
	public static void sort(Comparable[] a)
	{
		 //将a[]按升序排列
        int N=a.length;
        for(int i=0;i<N-1;i++)
            for(int j=0;j<N-i-1;j++)
                if(less(a[j+1],a[j])) exch(a,j,j+1);
	}
	//...
}
```
###希尔排序
希尔排序的思想是使数组中任意间隔为h的元素都是有序的。这样的数组被称为**h有序数组**。换句话说，一个h有序数组就是h哥互相独立的有序数组编织在一起组成的一个数组。
实现希尔排序的一种方法是对于每个h，用插入排序将h个子数组独立地排序。但因为子数组是相互独立的，一个更简单的方法是在h-子数组中将每个元素交换到比它大的元素之前去（将比它大的元素向右移动一格）。只需要在插入排序的代码中将移动元素的距离由1改为h即可。这样，希尔排序的实现就转化为了一个类似于插入排序但是用不同增量的过程。
```java
public class Shell
{
	public static void sort(Comparable[] a)
	{
		//将a[]按升序排序
		int N=a.length;
		int h=1;
		while(h<N/3)h=3*h+1;//1,4,13,40,121,364,1093,...
		while(h>=1)
		{
			//将数组变为h有序
			for(int i=h;i<N;i++)
			{
				//将a[i]插入到a[i-h],a[i-2*h],a[i-3*h]...之中
				for(int j=i;j>=h&&less(a[j],a[j-h]);j-h)
				exch(a,j,j-h);	
			}
			h=h/3;
		}
	}
	//...
}
```

###归并排序
归并，即将两个有序的数组归并成一个更大的有序数组。归并排序。要将一个数组排序，可以先（递归地）将它分成两半分别排序，然后将结果归并起来。

**原地归并的抽象方法**
```java
public static void merge(Comparable[] a,int lo,int mid,int hi)
{
	//将a[lo..mid]和a[mid+1..hi]归并
	int i=lo,j=mid+1；	
	for（int k=lo;k<=hi;k++)//将a[lo..hi]复制到aux[lo..hi]
		aux[k]=a[k];	
	for(int k=lo;k<=hi;k++)   //归并回到a[lo..hi]
		if(i>mid)                    a[k]=aux[j++];
		else if(j>hi)                a[k]=aux[i++];
		else if(less(aux[j],aux[i])) a[k]=aux[j++];
		else                         a[k]=aux[i++];
}
```

**自顶而下的归并排序**
```java
public class Merge
{
	private static Comparable[] aux;   //归并所需的辅助数组
	public static void sort(Comparable[] a)
	{
		aux=new Comparable[a.length];  //一次性分配空间
		sort(a,0,a.length-1);
	}
	private static void sort(Comparable[] a,int lo,int hi)
	{	//将数组a[lo..hi]排序
		if(hi<=lo) return;
		int mid=lo+(hi-lo)/2;
		sort(a,lo,mid);		//将左半边排序
		sort(a,mid+1,hi);	//将右半边排序
		merge(a,lo,mid,hi); //归并结果		
	}
}
```

**自底向上的归并排序**

```java
public class MergeBU
{
	private static Comparable[] aux;     //归并所需的辅助数组
	//merge()方法见原地归并的抽象方法
	public static void sort(Comparable[] a)
	{   //进行lgN次两两归并
		int N=a.length;
		aux=new Comparable[N];
		for(int sz=1;sz<N;sz=sz+sz)					//sz子数组大小
			for(int lo=0;lo<N-sz;lo+=sz+sz)			//lo：子数组索引
				merge(a,lo,lo+sz-1,Math.min(lo+sz+sz-1,N-1));		
	}
}
```

###快速排序
快速排序是一种分治的排序算法。它将一个数组分成两个子数组，将两部分独立地排序快速排序和归并排序是互补的。

**快速排序**

```java
public class Quick
{
	public static void sort(Comparable[] a)		
	{
		StdRandom.shuffle(a);		//消除对输入的依赖
		sort(a,0,a.length-1);
	}
	private static void sort(Comparable[]a ,int lo,int hi)
	{
		if(hi<=lo) return;
		int j=partition(a,lo,hi);	  //见快速排序的切分
		sort(a,lo,j-1);				//将左半部份a[lo..j-1]排序
		sort(a,j+1,hi);				//将右半部份a[j+1..hi]排序
	}
}
```
**快速排序的切分**
```java
private static int partition(Comparable[] a,int lo,int hi)
{	//将数组切分为a[lo..i-1],a[i],a[i+1..hi]
	int i=lo,j=hi+1;	//左右扫描指针
	Comparable v=a[lo];	//切分元素
	while(true)
	{	//扫描左右，检查扫描是否结束并交换元素
		while(less(a[++i],v)) if(i==hi)break;
		while(less(v,a[--j])) if(j==lo)break;
		if(i>=j) break;
		exch(a,i,j);		
	}
	exch(a,lo,j);		//将v=a[j]放入正确的位置
	return j;			//a[lo..j-1]<=a[j]<=a[j+1..hi]达成	
}
```
**算法改进**



