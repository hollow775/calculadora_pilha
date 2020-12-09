import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        String conta = "";
        int op = -1;
        while(op!=0) {
            //UM pequeno menu para o usu�rio escolher.
            System.out.println("--------Menu---------");
            System.out.println("1 - Infixa para PosFixa");
            System.out.println("2 - Infixa para PreFixa");
            System.out.println("0 - Para sair");
            op = Integer.parseInt(scan.nextLine());
            switch(op) {
                case 1:
                    System.out.println("Digite a expressao");
                    conta = scan.nextLine();
                    System.out.println(Posfixa.conPosfix(conta));
                    System.out.println("Quer fazer outra operacao?\n 1 - continuar\n 0- sair");
                    op = Integer.parseInt(scan.nextLine());
                    break;
                case 2:
                    System.out.println("Digite a expressao");
                    conta = scan.nextLine();
                    System.out.println(evaluatePrefix(infixToPrefix(conta)));
                    System.out.println("Quer fazer outra operacao?\n 1 - continuar\n 0- sair");
                    op = Integer.parseInt(scan.nextLine());
                    break;
            }
        }
    }
    // Se a funcao nao for um numero ou letra ela ser� um operador.
    static boolean isOperator(char c)
    {
        return (!(c >= 'a' && c <= 'z') &&
                !(c >= '0' && c <= '9') &&
                !(c >= 'A' && c <= 'Z'));
    }

    // Funcao para encontrar a prioridade do operador
    static int getPriority(char C)
    {
        if (C == '-' || C == '+')
            return 1;
        else if (C == '*' || C == '/')
            return 2;
        else if (C == '^')
            return 3;
        return 0;
    }

    // Funcao que ir� fazer a m�gica de transformar Infixa para Prefixa
    static String infixToPrefix(String infix)
    {
        // Pilha de operadores
        Stack<Character> operators = new Stack<Character>();

        // Pilha de operandos
        Stack<String> operands = new Stack<String>();

        for (int i = 0; i < infix.length(); i++)
        {

            // Se o caracter for um parentese esquerdo
            // entao adiciona ele no pilha de operadores
            if (infix.charAt(i) == '(')
            {
                operators.push(infix.charAt(i));
            }
            // Se o caracter for um parentese direito
            // Entao retira os elementos das duas pilhas e retorna o resultado
            // enquanto a lista de operadores nao for vazia ou encontrar um parentese esquerdo
            else if (infix.charAt(i) == ')')
            {
                while (!operators.empty() &&
                        operators.peek() != '(')
                {
                    // operando 1
                    String op1 = operands.peek();
                    operands.pop();

                    // operando 2
                    String op2 = operands.peek();
                    operands.pop();
                    // operador
                    char op = operators.peek();
                    operators.pop();

                    // identa o operador na frente dos dois operandos
                    String tmp = op + op2 + op1;
                    operands.push(tmp);
                }

                //Retira o parentese esquerdo da pilha
                operators.pop();
            }

            // Se o caracter foi um nao operando adiciona ele na pilha de operadores
            else if (!isOperator(infix.charAt(i)))
            {
                operands.push(infix.charAt(i) + "");
            }
            // Se o caracter atual for um operador coloca ele na lista de operadores depois
            //de retirar os operadores de maior prioridade da pilha de operadores e colocando
            // o resultado na pilha de operandos

            else
            {
                while (!operators.empty() &&
                        getPriority(infix.charAt(i)) <=
                                getPriority(operators.peek()))
                {

                    String op1 = operands.peek();
                    operands.pop();

                    String op2 = operands.peek();
                    operands.pop();

                    char op = operators.peek();
                    operators.pop();

                    String tmp = op + op2 + op1;
                    operands.push(tmp);
                }

                operators.push(infix.charAt(i));
            }
        }
        // Retira operadores da pilha de operadores at� que ela esteja vazia
        // e concatena o operador na frente dos dois primeiros n�meros encontrados.
        while (!operators.empty())
        {
            String op1 = operands.peek();
            operands.pop();

            String op2 = operands.peek();
            operands.pop();

            char op = operators.peek();
            operators.pop();

            String tmp = op + op2 + op1;
            operands.push(tmp);
        }

        // A express�o final prefix � retornada da pilha de operandos.
        return operands.peek();
    }
    static Boolean isOperand(char c)
    {
        // Se o caracter for um digito entao ele deve ser um operando
        if (c >= 48 && c <= 57)
            return true;
        else
            return false;
    }

    static double evaluatePrefix(String exprsn)
    {
        Stack<Double> Stack = new Stack<Double>();

        for (int j = exprsn.length() - 1; j >= 0; j--) {

            // Adiciona o operador na pilha
            if (isOperand(exprsn.charAt(j)))
                Stack.push((double)(exprsn.charAt(j) - 48));

            else {

                // Se um operador for encontrado retirado os dois primeiros
                // operandos da pilha
                double o1 = Stack.peek();
                Stack.pop();
                double o2 = Stack.peek();
                Stack.pop();
                //Usa o switch case para fazer as opera�oes
                switch (exprsn.charAt(j)) {
                    case '+':
                        Stack.push(o1 + o2);
                        break;
                    case '-':
                        Stack.push(o1 - o2);
                        break;
                    case '*':
                        Stack.push(o1 * o2);
                        break;
                    case '/':
                        Stack.push(o1 / o2);
                        break;
                }
            }
        }

        return Stack.peek();
    }

}
class Posfixa{
    public static int conPosfix(String expressao)
    {
        char[] caracter = expressao.toCharArray();
        // Stack for numeros: 'num'
        Stack<Integer> num = new Stack<Integer>();
        // Stack for Operadores: 'ops'
        Stack<Character> ops = new Stack<Character>();

        for (int i = 0; i < caracter.length; i++)
        {
            // Se o caracter atual for
            // um espa�o em branco pulamos ele
            if (caracter[i] == ' ')
                continue;
            // Se o caracter atual for um operando colocamos ele na pilha de operando
            if (caracter[i] >= '0' && caracter[i] <= '9'){
                StringBuffer sbuf = new StringBuffer();
                // Talvez tenha mais de um digito no numero
                while (i < caracter.length && caracter[i] >= '0' && caracter[i] <= '9')
                    sbuf.append(caracter[i++]);
                num.push(Integer.parseInt(sbuf.toString()));

                //Como i foi adicionado temos que reduzir i em 1 para continuar da mesma posi��o
                //no qual o while come�ou
                i--;
            }

            // Se o caracter for um parentese esquerdo coloca ele na pilha de operadores
            else if (caracter[i] == '(')
                ops.push(caracter[i]);

                // Se um parentese direito for encontrado resolve-se o parentese inteiro
            else if (caracter[i] == ')')
            {
                while (ops.peek() != '(')
                    num.push(calcularOp(ops.pop(), num.pop(), num.pop()));
                ops.pop();
            }

            // Caracter atual � um operador...
            else if (caracter[i] == '+' || caracter[i] == '-' ||caracter[i] == '*' || caracter[i] == '/'){
                //Enquanto o topo de ops tiver o a mesma preferencia ou mais do mesmo
                //caracter, que � um operador, resolve o operador com os dois primeiros valores
                //na pilha num
                while (!ops.empty() && temPrecedencia(caracter[i], ops.peek()))
                    num.push(calcularOp(ops.pop(), num.pop(), num.pop()));
                //Adiciona o caracter atual na pilha ops
                ops.push(caracter[i]);
            }
        }

        // Toda a express�o ja foi ajeitada nesse momento, faltando somente terminar de fazer o
        //calculos
        while (!ops.empty())
            num.push(calcularOp(ops.pop(), num.pop(), num.pop()));

        //o Topo de num possui o resultado, retorne ele.
        return num.pop();
    }

    //Retorna true se op2 tiver precendencia maior ou a mesma de op1.
    //se n�o retorna falso
    public static boolean temPrecedencia(char op1, char op2){
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }
    //metodo que calcula
    public static int calcularOp(char op, int b, int a){
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new UnsupportedOperationException("Imposs�vel dividir por 0");
                return a / b;
        }
        return 0;
    }
}
