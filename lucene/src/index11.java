import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;

import static org.apache.lucene.index.DirectoryReader.*;

//TODO:FOLLOW CODING CONVENTION
class LuceneAddElement {
    public static void main(String[] args) throws IOException, ParseException {

       new StandardAnalyzer();
        //directory is loaction of path
        Directory dir = FSDirectory.open(Paths.get("C:/Users/GOPAL PARMAR/Desktop/index1/index"));
        //indexWriterconfig StandardAnalyzer is analyzed text into token
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(  new StandardAnalyzer());
        //create indexing
        IndexWriter indexWriter = new IndexWriter(dir, indexWriterConfig);
        //add data in field using add method
        addDoc(indexWriter, 1, "Abhay parmar", "160041",  70.41f,71.56);
        addDoc(indexWriter, 2, "gopal parmar", "160042",75.67f,72.63);
        addDoc(indexWriter, 3, "harshit kotadiya", "160043",80.96f,86.33);
        addDoc(indexWriter, 4, "jayal sikotra", "160044",97.56f,96.23);
        addDoc(indexWriter, 5, "nilesh nakum", "160045",99.45f,96.56);
        addDoc(indexWriter, 5, "parmar gopal", "160046",98.63f,98.45);
        //close indexwriter
        indexWriter.close();

        //create term class
        Term term=new Term("name","abhay");
        //searchin query using termquery
        Query q=new TermQuery(term);
        //termrangeQuery
        TermRangeQuery termRangeQuery=new TermRangeQuery("name",new BytesRef("a"),new BytesRef("n"),true,false);

        //prefixQuery
        /* Term term=new Term("name","gopa");
         PrefixQuery q= new PrefixQuery(term);*/
        //FuzzyQuery
        /*   Term term=new Term("name","parkak");
            Query q = new FuzzyQuery(term);*/
        //wildcardQuery
        /*  Term term=new Term("name","g*l");
          Query q=new WildcardQuery(term);*/

        //indexreader read indexing
        IndexReader reader = open(dir);
        //indexsearcher is search element in reader object
        IndexSearcher searcher = new IndexSearcher(reader);
        //topdocs point n number of result
        TopDocs docs = searcher.search(q,100);
       //hits of search
        ScoreDoc[] hits = docs.scoreDocs;
        //for loop
        for (int i=0;i<hits.length;++i)
        {
            //hits element indocument
            int docId = hits[i].doc;
            //searche id in doc
            Document d = searcher.doc(docId);
            //print value of quetion field
            System.out.println(d.get("name"));
        }
        //close index reader
        reader.close();
    }

    private static void addDoc(IndexWriter w,int id, String name, String rollNo,float sscMarks,double hscMarks) throws IOException{
        //create document
        Document doc = new Document();
        //add id
        doc.add(new IntPoint("id",100));
        //add name
        doc.add(new TextField("name", name, Field.Store.YES));
        //add rollno
        doc.add(new StringField("rollNo", rollNo, Field.Store.YES));
        //add sscMarks
        doc.add(new FloatPoint("sscMarks", sscMarks));
        //add hscMarks
        doc.add(new DoublePoint("hscMark", hscMarks));
        //add document in adddocument Method
        w.addDocument(doc);

    }

}
