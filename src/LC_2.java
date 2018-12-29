//import java.util.Scanner;
//public class LC_2
//{
//    public static void main(String args[])
//    {
//        String input = null;
//        Scanner sc = new Scanner(System.in);
//        input = sc.nextLine();
//        String[] split = input.split("\\+");
//        int res = 0;
//        for(String temp : split)
//        {
//           int num = 0;
//           int pow = 0;
//           for(int i = 0;i < temp.length();i++)
//           {
//               char c = temp.charAt(i);
//               if(c >= '0' && c <= '9')
//               {
//                   num += (c -'0') * Math.pow(10.0,pow);
//                   pow++;
//               }
//           }
//           res += num;
//        }
//        String sres = String.valueOf(res);
//        for(int i = sres.length()-1;i >= 0;i--)
//        {
//            System.out.print(sres.charAt(i));
//            if(i > 0)
//                System.out.print(" -> ");
//        }
//    }
//}
/* -----------------------------------
 *  WARNING:
 * -----------------------------------
 *  Your code may fail to compile
 *  because it contains public class
 *  declarations.
 *  To fix this, please remove the
 *  "public" keyword from your class
 *  declarations.
 */

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
import java.io.*;
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
 }

class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode t1 = l1,t2 = l2;
        ListNode l3 = null;
        ListNode t3 = l3;
        int addnum = 0;
        while(t1 != null || t2 != null)
        {
            int num1 = 0;
            int num2 = 0;
            int lastadd = addnum;
            if(t1 == null)
            {
                num1 = 0;
                num2 = t2.val;
            }
            else if( t2 == null)
            {
                num1 = t1.val;
                num2 = 0;
            }
            else
            {
                num1 = t1.val;
                num2 = t2.val;
            }
            int val = (num1 + num2 + lastadd)%10;
            addnum = (num1 + num2 + lastadd)/10;
            ListNode temp = new ListNode(val);
            if(t3 == null)
            {
                t3 = temp;
                l3 = temp;
            }
            else
            {
                t3.next = temp;
                t3 = temp;
                t3.next = null;
            }
            if(t1 != null)
                t1 = t1.next;
            if(t2 != null)
                t2 = t2.next;
        }
        if(addnum > 0)
        {
            ListNode temp = new ListNode(addnum);
            if(t3 == null)
            {
                t3 = temp;
                l3 = temp;
            }
            else
            {
                t3.next = temp;
                t3 = temp;
                t3.next = null;
            }
        }
        return l3;
    }
}

public class LC_2 {
    public static int[] stringToIntegerArray(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return new int[0];
        }

        String[] parts = input.split(",");
        int[] output = new int[parts.length];
        for(int index = 0; index < parts.length; index++) {
            String part = parts[index].trim();
            output[index] = Integer.parseInt(part);
        }
        return output;
    }

    public static ListNode stringToListNode(String input) {
        // Generate array from the input
        int[] nodeValues = stringToIntegerArray(input);

        // Now convert that list into linked list
        ListNode dummyRoot = new ListNode(0);
        ListNode ptr = dummyRoot;
        for(int item : nodeValues) {
            ptr.next = new ListNode(item);
            ptr = ptr.next;
        }
        return dummyRoot.next;
    }

    public static String listNodeToString(ListNode node) {
        if (node == null) {
            return "[]";
        }

        String result = "";
        while (node != null) {
            result += Integer.toString(node.val) + ", ";
            node = node.next;
        }
        return "[" + result.substring(0, result.length() - 2) + "]";
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            ListNode l1 = stringToListNode(line);
            line = in.readLine();
            ListNode l2 = stringToListNode(line);
            ListNode ret = new Solution().addTwoNumbers(l1, l2);

            String out = listNodeToString(ret);

            System.out.print(out);
        }
    }
}
