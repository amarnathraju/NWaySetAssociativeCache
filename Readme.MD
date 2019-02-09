# N-Way Set Associative Cache

This piece of software can be used as a layer of cache in memory to store any predefined types of keys and values. 

###Example functional usage
As an in-memory cache on an application server, storing data associated with user id, in order to avoid database dip for every request.


### Prerequisites
Java 8
* JUnit4 for Testing

### Building Artifacts : Generating the Jar (using command-line)
1) This creates the *.class files from the Source code
    >javac <path_to_the_code>/Cache/src/main/*/*.java

2) We create a manifest file 'manifest.txt' with the following line 'Manifest-Version: 1.0'

3) To create jar named 'Cache.jar' from the *.class file. 
    >jar cvfm Cache.jar manifest.txt <path_to_the_code>/Cache/src/main/*/*.class

You can now use the jar file that had been created for your application. 

## Deployment
If you have a Jar file, you can add it to your classpath and use the software as a library.

### Built-in replacement Algorithms
The LRU and MRU algorithms have been implemented for the user to be used directly.

### Example usage:
The user should create an instance of the class 'main.app.NwayCacheApp' to use the Application
> NwayCacheApp<K,V> cache = new NwayCacheApp<>(N, S, algoString);

Here,
* N is the number of sets to be created for the storage of data
* S is the size of each of those sets. Is S=10, each Set has 10 lines.
* algoString is where the user can provide the replacement algorithm. This could either be a custom user-defined algorithm of "LRU"/"MRU" to use the existing algorithms

> cache.put(key,val)

This puts the key-value pair into the cache.
Here,
* key is of type K and not equal to null
* val is of type V and not equal to null

> cache.get(key)

This gets an Optional object that either contains the value for the given key or is absent.
Here,
* key is of type K and not equal to null

#### Writing your own custom replacement algorithm
The user should extend the abstract class "main.model.NSet" for creating a new replacement algorithm. The user should also override the following abstract methods.
>      public abstract Wrapper<K,V> getWrapper(K key);
  
>      public abstract void putWrapper(K key, V val);
  
>      public abstract void removeLogic();

To use the custom created replacement algorithm when you create an instance for 'NwayCacheApp' pass in the 'Classpath' as the 3rd argument (i.e. algoString).
For Example : "main.app.LRUSet".

The user is provided the flexibility to use a Wrapper of their own choice to store the key-value pairs.
The wrapper class should extend the abstract class "main.model.Wrapper".
If you do not want to create new Wrapper the library provides you with a Sample Wrapper i.e. "main.util.SimpleWrapper"
A Wrapper class could potentially be used to determine the removal logic of the element when set is full.



## Authors
* **Amarnath Raju Vysyaraju**

