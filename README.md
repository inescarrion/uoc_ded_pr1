# First Project (PR1) - Design of Data Structures subject 
***Student:** Inés Carrión Orosa*

## Table of contents
1. <a href="#general-info">General info</a>
2. <a href="#new-tests">New tests</a>
3. <a href="#modifications-comments">Modifications and additional comments</a>
  <br>3.1. <a href="#new-methods-var-aux">New methods, variables and auxiliary classes</a>
  <br>3.2. <a href="#new-tads">New TADs: DictionaryOrderedVector and OrderedVector</a>
  <br>3.3. <a href="#problems">Problems found</a>

<h2 id="general-info">General info</h2>
<p>This project was made for the subject "Diseño de Estructura de Datos (Design of Data Structures)" taught at <a href="https://www.uoc.edu/portal/es/index.html">Universitat Oberta de Catalunya</a> (UOC).</p>
<p>The goal of this project is the implementation of the TAD specified in the previous task (PEC1), that would allow a club to store and manage his sport events and all the related data.</p>
<p>In the folder <i>src</i> there's a package called <i>uoc.ds.pr</i> that contains the main classes (the interface and the implementation of the <code>SportEvents4Club</code> TAD) and the following subpackages:</p>
<ul>
  <li><i>uoc.ds.pr.exceptions</i>: contains the generic exception class <code>DSException</code> and all the exceptions which have it as a parent and which are thrown when there is an error.</li>
  <li><i>uoc.ds.pr.model</i>: contains the classes that represent relevant entities of the model like players, organizing entities, sport events, ratings of the sport events or files to propose a new sport event.
    <ul>
      <li><code>Player</code></li>
      <li><code>OrganizingEntity</code></li>
      <li><code>SportEvent</code></li>
      <li><code>Rating</code></li>
      <li><code>File</code></li>
    </ul>
  </li>
  <li><i>uoc.ds.pr.util</i>: contains extra classes that are used by the previous ones, like two TADs to create ordered vectors and a class to manage the sport events resources.
    <ul>
    <li><code>DictionaryOrderedVector</code></li>
    <li><code>OrderedVector</code></li>
    <li><code>ResourceUtil</code></li>
    </ul>
  </li>
</ul>
<p>Furthermore, in the folder <i>test</i> there is also a package called <i>uoc.ds.pr</i> that contains the main test class with the provided tests (<code>SportEvents4ClubPR1Test</code>), a factory class that creates all the objects used in the provided tests (<code>FactorySportEvents4Club</code>), a class with more tests that were added as requested (<code>ExtraTests</code>) and the subpackage <i>uoc.ds.pr.util</i> that contains a class with methods to assist in managing dates and a class to tests the <code>ResourceUtil</code> class.</p>


<h2 id="new-tests">New tests</h2>
<p>The <code>ExtraTests</code> class was created to test some situations that are not covered in the main test 
class (<code>SportEvents4ClubPR1Test</code>).</p>
<p>The structure is similar to the main test class, with a <code>setUp</code> method and a<code>tearDown</code> method that run before and after every test respectively.</p>
<p>Then, there are 10 new tests, which are the following:</p>
<ul>
  <li><code>updateFileEmptyQueue</code>: checks if an exception is thrown when trying to update a file and the files queue is empty.</li>
  <li><code>signUpEventPlayerException</code>: checks if an exception is thrown when nonexistent player tries to sign up in a sport event.</li>
  <li><code>signUpEventSportEventException</code>: checks if an exception is thrown when the player tries to sign up in nonexistent sport event.</li>
  <li><code>addRatingPlayerException</code>: checks if an exception is thrown when a nonexistent player tries to add rating.</li>
  <li><code>addRatingSportEventException</code>: checks if an exception is thrown when the player tries to add rating to nonexistent sport.</li>
  <li><code>addRatingPlayerNotInEventException</code>: checks if an exception is thrown when the player tries to add rating to the event without being registered in it.</li>
  <li><code>getRatingsByEventException</code>: checks if an exception is thrown when trying to add rating to nonexistent sport event</li>
  <li><code>bestSportEventException</code>: checks if an exception is thrown when best rated sport event is looked up because bestSportEvent vector is empty</li>
  <li><code>sameRatingTest</code>: checks if best rated sport event is the first rated despite there are two events with the same rating</li>
  <li><code>substituteSportEventsList</code>: checks if substitute players don't have the sport event where they are
  substitutes in its list</li>
</ul>


<h2 id="modifications-comments">Modifications and additional comments</h2>
<h3 id="new-methods-var-aux">New methods, variables and auxiliary classes</h3>
<p>As said in the guidelines, there are some new methods in the <code>SportEvents4Club</code> TAD that were not mentioned in the PEC1 but are created to help with getting some relevant values and ease testing.</p>
<p>Moreover, there are some new variables that store some structures and the number of elements in them. This variables were added in some entities of the model as for example Player, that has a variable with a LinkedList of the sport events in which is enrolled and a variable that stores the number of elements in that list. There are is also an <code>addXXX</code> method that inserts a new element in that structures and increments the variable that stores the number of elements.</p>
<p>Finally, a new class auxiliary class called <code>ResourceUtil</code> has been created and implemented to manage the resources in sport events. This resources are stored in a byte variable that has different values according to the active flags (using binary values). The value of the variable is calculated in the constructors of the class using logical operators and there is one method per flag that return true if the flag is enabled or false if the flag is disabled.</p>

<h3 id="new-tads">New TADs: DictionaryOrderedVector and OrderedVector</h3>
<p>As we saw previously, there are two TADs that were created in order to have ordered vectors to store the sport events (<code>DictionaryOrderedVector</code>) and the best rated sport events (<code>OrderedVector</code>).</p>
<p>The <code>DictionaryOrderedVector</code> class extends the <code>DictionaryArrayImpl</code> class which is part of the TADs library provided and which implements another classes of that library (<code>Dictionary</code> and <code>FiniteContainer</code>). In this way, it uses the SportEvent id as Key (K) and a SportEvent object as Value (V), so that the sport events are ordered by the id and with the order established by the comparator passed in the initialization as a parameter. It has two methods:</p>
<ul>
  <li><code>public void put(K key, V value)</code>: inserts a new element at the end of the vector and swaps the element with the previous one until the vector is ordered.</li>
  <li><code>public V get(K key):</code> returns the value of the element that corresponds to the key passed as a parameter. Since the vector is ordered, it uses a binary search algorithm to find the element.</li>
</ul>
<p>The <code>OrderedVector</code> class implements the <code>FiniteContainer</code> class which is part of the TADs library provided. In this way, it is a normal vector that uses some methods to have ordered its elements as it is
established by the comparator passed in the initialization as a parameter. The <code>put</code> method inserts a new element at the end of the vector and swaps the element with the previous one until the vector is ordered. Another important method is <code>update</code>, which finds and moves the element passed as a parameter to the right or to the left until the vector is ordered. This method is used if the value used to do the ordering was updated in that element and returns true if the element was found and false if the element wasn't found.</p>

<h3 id="problems">Problems found</h3>
<p>There are also some problems found with this implementation, like for example the fact that a player enrolled in a sport event cannot be deleted from the SportEvent enrollments queue, so the substitutes can never participate in that sport event. </p>
<p>Because of this and by not being said anything in the guidelines or tests provided, it has been assumed that the players signed up as substitutes do not save the event in the list of events in which they participate. That's why
a test has been created (the last one of the <code>ExtraTests</code> class) which checks if this situation is happening as described.</p>
<p>There's also a discrepancy compared to what is described in the PEC1 solution, as the values of the Rating enum
of the <code>SportEvents4Club</code> interface are only up to 5 when it was said the rating was a number between 1 and 10. This enum was not modified because it was provided that way and the test use values only between 1 and 5.</p>
