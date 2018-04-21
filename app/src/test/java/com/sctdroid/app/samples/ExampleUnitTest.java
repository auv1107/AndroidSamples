package com.sctdroid.app.samples;

import com.facebook.stetho.inspector.elements.NodeType;
import com.sctdroid.app.samples.modules.retro.SimpleRequest;
import com.sctdroid.app.samples.modules.retro.library.HttpCall;
import com.sctdroid.app.samples.modules.retro.library.ObservableCallAdapter;
import com.sctdroid.app.samples.modules.retro.library.Response;
import com.sctdroid.app.samples.modules.retro.library.Retro;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import static java.sql.DriverManager.println;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void replaceSpace() {
        assertEquals("We%20Are%20Happy", replaceSpace(new StringBuffer("We Are Happy")));
    }

    public String replaceSpace(StringBuffer str) {
        int lastMatch = -1;
        String string = str.toString();
//        do {
//            str.replace(lastMatch + 1, str.length(), string);
//
//        } while (lastMatch != -1);
        return string.replace(" ", "%20");
    }

    @Test
    public void printListFromTailToHead() {
    }

    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {

        if (listNode == null) {
            return new ArrayList<>();
        }

        ArrayList<Integer> result = printListFromTailToHead(listNode.next);
        result.add(listNode.val);
        return result;
    }


    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }

    public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        // find head in in
        if (pre.length == 1) {
            return new TreeNode(pre[0]);
        }
        int head = pre[0];
        int index = 0;
        while (head != in[index]) {
            index ++;
        }
        TreeNode node = new TreeNode(head);
        // reconstruct left
        if (index > 0) {
            int[] newPre = new int[index];
            int[] newIn = new int[index];
            int n = 0;
            while (n < index) {
                newPre[n] = pre[n + 1];
                newIn[n] = in[n];
                n ++;
            }

            node.left = reConstructBinaryTree(newIn, newPre);
        }
        // reconstruct right
        if (index < pre.length) {
            int[] newIn = new int[pre.length - index - 1];
            int[] newPre = new int[pre.length - index - 1];
            int n = 0;
            while (n < newIn.length) {
                newIn[n] = in[index + 1 + n];
                newPre[n] = pre[index + 1 + n];
                n ++;
            }
            node.right = reConstructBinaryTree(newPre, newIn);
        }
        // combined them
        return node;
    }

    @Test
    public void bubbleSort() {
        int[] array = new int[]{32,324,54,35,4,1,432,3,2,5,0,3142};
//        bubbleSort(array);
//        mergeSort(array);
//        quickSort(array);
        heapSort(array);
        for (int n : array) {
            System.out.print(n);
            System.out.print(" ");
        }
    }

    public void bubbleSort(int[] array) {
        int i, j, temp, len = array.length;
        boolean changed = true;
        for (i = 0; i < len - 1 && changed; i++) {
            changed = false;
            for (j = 0; j < len - i - 1; j++) {
                if (array[j] > array[j+1]) {
                    array[j] ^= array[j+1];
                    array[j+1] ^= array[j];
                    array[j] ^= array[j+1];
                    changed = true;
                }
            }
        }
    }

    public void mergeSort(int[] array) {
        int len = array.length;
        int[] result = new int[len];
        bubbleSortRecursive(array, result, 0, len - 1);
    }

    private void mergeSortRecursive(int[] array, int[] result, int start, int end) {
        if (start >= end) {
            return;
        }
        int len = end - start, mid = (len >> 1) + start;
        int start1 = start, end1 = mid;
        int start2 = mid + 1, end2 = end;
        int k = start;

        mergeSortRecursive(array, result, start1, end1);
        mergeSortRecursive(array, result, start2, end2);

        while (start1 <= end1 && start2 <= end2) {
            result[k++] = array[start1] < array[start2] ? array[start1++] : array[start2++];
        }
        while (start1 <= end1) {
            result[k++] = array[start1++];
        }
        while (start2 <= end2) {
            result[k++] = array[start2++];
        }
        for (k = start; k <= end; k++) {
            array[k] = result[k];
        }
    }


    public void bubbleSortRecursive(int[] arr, int[] result, int start, int end) {
        if (start >= end) {
            return;
        }

        int len = end - start, mid = (len >> 1) + start;
        int start1 = start, end1 = mid;
        int start2 = mid + 1, end2 = end;

        mergeSortRecursive(arr, result, start1, end1);
        mergeSortRecursive(arr, result, start2, end2);

        int k = start;
        while(start1 <= end1 && start2 <= end2) {
            result[k++] = arr[start1] < arr[start2] ? arr[start1++] : arr[start2++];
        }
        while (start1 <= end1) {
            result[k++] = start1++;
        }
        while (start2 <= end2) {
            result[k++] = start2++;
        }
        for (k = start; k < end; k++) {
            arr[k] = result[k];
        }
    }

    public void quickSort(int[] arr) {
        quickSortRecursive(arr, 0, arr.length - 1);
    }

    public void quickSortRecursive(int[] arr, int start, int end) {
        if (start >= end || arr.length <= 1) {
            return;
        }
        int pivot = arr[(start + end) / 2];
        int i = start, j = end;
        while (i <= j) {
            while (arr[i] < pivot) {
                i++;
            }
            while (arr[j] > pivot) {
                j--;
            }
            if (i < j) {
                arr[i] ^= arr[j];
                arr[j] ^= arr[i];
                arr[i] ^= arr[j];
                i++;
                j--;
            } else if (i == j){
                i++;
            }
        }
        quickSortRecursive(arr, start, j);
        quickSortRecursive(arr, i, end);
    }

    public void heapSort(int[] arr) {
        // init max heap
        int len = arr.length - 1;
        int startIndex = (len - 1) >> 1;
        while (startIndex >= 0) {
            heapify(arr, startIndex--, len);
        }
        // move 0 to len, resort
        while (len > 0) {
            swap(arr, 0, len);
            heapify(arr, 0, --len);
        }
    }

    public void swap(int[] arr, int i, int j) {
        arr[i] ^= arr[j];
        arr[j] ^= arr[i];
        arr[i] ^= arr[j];
    }

    public void heapify(int[] arr, int index, int len) {
        int li = (index << 1) + 1;
        int ri = li + 1;
        int cMax = li;

        if (li > len) return;
        if (ri <= len && arr[ri] > arr[li]) {
            cMax = ri;
        }
        if (arr[cMax] > arr[index]) {
            swap(arr, index, cMax);
            heapify(arr, cMax, len);
        }
        // swap with parent or not
            // swap
            // reheap
    }

    private void maxHeapify(int[] arr, int index,int len){
        int li = (index << 1) + 1; // 左子节点索引
        int ri = li + 1;           // 右子节点索引
        int cMax = li;             // 子节点值最大索引，默认左子节点。

        if(li > len) return;       // 左子节点索引超出计算范围，直接返回。
        if(ri <= len && arr[ri] > arr[li]) // 先判断左右子节点，哪个较大。
            cMax = ri;
        if(arr[cMax] > arr[index]){
            swap(arr, cMax, index);      // 如果父节点被子节点调换，
            maxHeapify(arr, cMax, len);  // 则需要继续判断换下后的父节点是否符合堆的特性。
        }
    }
    @Test
    public void testAnnotationUrl(){
        SimpleRequest simpleRequest = new Retro.Builder()
                .callAdapter(new ObservableCallAdapter())
                .build()
                .create(SimpleRequest.class);
        simpleRequest.body("http://www.baidu.com").addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (arg instanceof Response) {
                    if (!((Response) arg).isError()) {
                        System.out.println(((Response) arg).getResponseBodyAsString());
                    }
                }
            }
        });
    }
}
