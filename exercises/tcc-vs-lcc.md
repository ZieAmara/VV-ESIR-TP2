# TCC *vs* LCC

Explain under which circumstances *Tight Class Cohesion* (TCC) and *Loose Class Cohesion* (LCC) metrics produce the same value for a given Java class. Build an example of such as class and include the code below or find one example in an open-source project from Github and include the link to the class below. Could LCC be lower than TCC for any given class? Explain.

## Answer

> Tight Class Cohesion (TCC) and Loose Class Cohesion (LCC) are other two well known and used metrics to evaluate the cohesion of a class.
> Both these metrics start by creating a graph from the class. The graph is constructed as follows: Given a class C, each method m declared in the class becomes a node. Given any two methods m and n declared in C we add an edge between m and n if and only if, m and n use at least one instance variable in common.
> TCC is defined as the ratio of directly connected pairs of node in the graph to the number or all pairs of nodes.
> On its side, LCC is the number of pairs of connected (directly or indirectly) nodes to all pairs of node. As before, constructors are not used.

### Circumstances where TCC and LCC produce the same value

> This happens when all the methods in a class reference the same internal attributes of the class, thus creating a strong dependence between the methods.
> Which is equivalent to saying that each method depends on each other method. In this case, the number of method pairs that share at least one field (TCC) is equal to the total number of dependencies between methods (LCC).
```
public class ExampleClass {
    private int variable1;
    private String variable2;

    public void method1(int new_var1) {
        this.variable1 = new_var1;
    }

    public void method2(String new_var2) {
        this.variable2 = new_var2;
    }

    public void method3(int new_var1, String new_var2) {
        this.variable1 = new_var1;
        this.variable2 = new_var2;
    }
}
```
<img width="578" alt="image" src="https://github.com/ZieAmara/VV-ESIR-TP2/assets/90223980/b3119aba-c1d8-4203-871a-5459f651e08b">

> In this example, all methods refer to variable1, creating strong cohesion within the class. Both TCC and LCC will produce the same value, because there is a close dependence between the methods.

### Could LCC be lower than TCC for any given class?

> The LCC can never be lower than the TCC for a given class. Indeed, by their definition we have the `LCC = (d_dir + d_indir)/N` and `TCC = (d_dir)/N` where *d_dir* corresponds to the total number of direct dependencies between the methods of the class and *d_indir* corresponds to the total number of indirect dependencies between the methods of the class. We can therefore conclude that we will always have *LCC ≥ TCC*
