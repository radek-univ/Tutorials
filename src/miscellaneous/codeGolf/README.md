## Challenge 2 – Code golf

Some programmers like to play so called 'code golf' (see for example [codegolf.stackexchange.com](https://codegolf.stackexchange.com)). 
The objective of that game is to come up with the shortest possible functions solving a given problem – every letter or space in the code counts.

### Example
Consider the following task:
> Write a function in Scala which, given a value `n`, prints or outputs as a `String` a triangle of size `n x n` made of symbols `#`. For `n = 6`, the expected output is
> ```
> #
> ##
> ###
> ####
> #####
> ######
> ```
> Each line of output may end with arbitrary number of spaces or other invisible characters. 
 
Of course this can be easily done with a simple loop in Scala:
```scala
def solution(n: Int): Unit = {
  var i = 0
  while (i < n) {
    var j = 0
    while (j <= i) {
      print("#")
      j += 1
    }
    print("\n")
    i += 1
  }
}
```
However, this is not an optimal solution from the point of view of code golf, as it uses `164` characters. We can do much better:
```scala
def s(n:Int):String=(1 to n).map("#"*_).mkString("\n")
```
Above solution uses only 54 bytes. But is it optimal? No! It turns out that 52 characters suffice.
```scala
def s(n:Int):String=1 to n map("#"*_) mkString("\n")
```
Can it be done even more briefly? You will never know until you find a way to shorten it. 
In this case, we can go down to 45 bytes!

```scala
def t(x:Int)=1.to(x).map("#"*_) mkString "\n"
```

When you're out of ideas for shortening your code, you may look here for inspiration: [Tips for golfing in Scala](https://codegolf.stackexchange.com/questions/3885/tips-for-golfing-in-scala)

And with fresh inspiration, we obtain 42 bytes!

```scala
def u=1.to(_:Int).map("#"*_) mkString "\n"
```
As nobody said that the function has to be given a name, we can get rid of `def u=` and have 36 bytes!
```scala
1.to(_:Int).map("#"*_) mkString "\n"
```
Is this the limit? And do you feel the fun of `code golf` yet? :)

-------

## The challenge

> Write a Scala function with one parameter `n: Int`, which produces (either printing to the console window or outputting as a String) an ASCII-chessboard of size `n`.
> A reference program is provided in `NotVeryShort.scala`, your solution should produce similar results – printable characters need to match, but each line of your program's output can end with arbitrary number of additional invisible characters (like spaces).
> 
> **Example outputs**
> - `n = 4`
>   ```
>          /\       
>         //\\      
>        /\\//\     
>       /  \/  \    
>      /\  /\  /\   
>     //\\//\\//\\  
>    /\\//\\//\\//\ 
>   /  \/  \/  \/  \
>   \  /\  /\  /\  /
>    \//\\//\\//\\/ 
>     \\//\\//\\//  
>      \/  \/  \/   
>       \  /\  /    
>        \//\\/     
>         \\//      
>          \/       
>   ```
> - `n = 9` 
>   ```
>                    /\                 
>                   //\\                
>                  /\\//\               
>                 /  \/  \              
>                /\  /\  /\             
>               //\\//\\//\\            
>              /\\//\\//\\//\           
>             /  \/  \/  \/  \          
>            /\  /\  /\  /\  /\         
>           //\\//\\//\\//\\//\\        
>          /\\//\\//\\//\\//\\//\       
>         /  \/  \/  \/  \/  \/  \      
>        /\  /\  /\  /\  /\  /\  /\     
>       //\\//\\//\\//\\//\\//\\//\\    
>      /\\//\\//\\//\\//\\//\\//\\//\   
>     /  \/  \/  \/  \/  \/  \/  \/  \  
>    /\  /\  /\  /\  /\  /\  /\  /\  /\ 
>   //\\//\\//\\//\\//\\//\\//\\//\\//\\
>   \\//\\//\\//\\//\\//\\//\\//\\//\\//
>    \/  \/  \/  \/  \/  \/  \/  \/  \/ 
>     \  /\  /\  /\  /\  /\  /\  /\  /  
>      \//\\//\\//\\//\\//\\//\\//\\/   
>       \\//\\//\\//\\//\\//\\//\\//    
>        \/  \/  \/  \/  \/  \/  \/     
>         \  /\  /\  /\  /\  /\  /      
>          \//\\//\\//\\//\\//\\/       
>           \\//\\//\\//\\//\\//        
>            \/  \/  \/  \/  \/         
>             \  /\  /\  /\  /          
>              \//\\//\\//\\/           
>               \\//\\//\\//            
>                \/  \/  \/             
>                 \  /\  /              
>                  \//\\/               
>                   \\//                
>                    \/                 
>   ```

The solution I came up with (slightly optimised, but perhaps not the shortest) has length 344 bytes. Can you do better? :)

If you find a solution, please share it with me and I will update the counter below:

### BEST SIZE OF STUDENT'S SOLUTION SO FAR = null
### BEST SIZE OF TUTOR'S SOLUTION SO FAR = 344

The challenge is open until the end of February. Good luck!