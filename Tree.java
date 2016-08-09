/*
 * Copyright (C) 2016 Michael <GrubenM@GMail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *
 * @author Michael <GrubenM@GMail.com>
 */
public class Tree {
    protected class Node {
        String data;
        Node right, left;
        public Node(String s) {
            data = s;
            right = left = null;
        }
        public Node(char ch) {
            this(String.valueOf(ch));
        }
    }
    Node root;
    String eqn;
    int pos;
    char ch;
    public void parse(String e) {
        eqn = e;
        pos = 0;
        getChar();
        root = findExpr();
    }
    protected char getChar() {
        if (pos < eqn.length()) {
            return ch = eqn.charAt(pos++);
        } return ch='\0';
    }
    protected Node findExpr() {
        Node tmproot = findProd();
        while (ch=='+' || ch=='-') {
            Node tmp = new Node(ch);
            getChar();
            tmp.left = tmproot;
            tmp.right = findProd();
            tmproot = tmp;
        }
        return tmproot;
    }
    protected Node findProd() {
        Node tmproot = findTerm();
        while (ch=='*' || ch=='/') {
            Node tmp = new Node(ch);
            getChar();
            tmp.left = tmproot;
            tmp.right = findTerm();
            tmproot = tmp;
        }
        return tmproot;
    }
    protected Node findTerm() {
        if (ch=='x') {
            Node tmp = new Node(ch);
            getChar();
            return tmp;
        }
        if (ch >= '0' && ch <= '9') {
            StringBuilder sb = new StringBuilder();
            for (; ch >= '0' && ch <= '9';) {
                sb.append(ch);
                getChar();
            }
            return new Node(sb.toString());
        }
        return null;
    }
    protected float x;
    public float calc(float x) {
        this.x = x;
        return calc(root);
    }
    protected float calc(Node root) {
        switch(root.data.charAt(0)) {
            case '+': return calc(root.left) + calc(root.right);
            case '-': return calc(root.left) - calc(root.right);
            case '*': return calc(root.left) * calc(root.right);
            case '/': return calc(root.left) / calc(root.right);
            case 'x': return x;
        }
        return Float.parseFloat(root.data);
    }
}
