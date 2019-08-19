package d.technoidentity.com.rxjava;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private String TAG="MAINActivity";
 private String sample ="check samples";
 private Observable<String> rxObservableObject;
 private Observable<Student> rxObservableStudentObject;
 private DisposableObserver<String> rxObserver;
 private DisposableObserver<Student> rxObserver2;
 private CompositeDisposable compositeDisposable = new CompositeDisposable();

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv= findViewById(R.id.text_id);

        rxObservableObject=  Observable.just(sample);

//           compositeDisposable.add(
//               rxObservableObject
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(getObserver()));



        rxObservableStudentObject = Observable.create(new ObservableOnSubscribe<Student>() {
            @Override
            public void subscribe(ObservableEmitter<Student> emitter) throws Exception {
                ArrayList<Student> data = getStudentData();
                for(Student studdent : data ) {
                    emitter.onNext(studdent);
                }

            }

        });


           compositeDisposable.add(
               rxObservableStudentObject
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .map(new Function<Student, Student1>() {
                       /**
                        * Apply some calculation to the input value and return some other value.
                        *
                        * @param student the input value
                        * @return the output value
                        * @throws Exception on error
                        */
                       @Override
                       public Student1 apply(Student student) throws Exception {
                           Student1 s1 = new Student1();
                           s1.setAge("as");
                           return s1;
                       }
                   })
                   .subscribeWith(getStudentObserver())
           );

    }

    private ArrayList<Student> getStudentData() {
        ArrayList<Student> studentArrayList = new ArrayList<>();
        Student s = new Student();
        s.setAge("12");
        s.setName("12121");

        Student s1 = new Student();
        s1.setAge("12");
        s1.setName("12121");

        Student s2 = new Student();
        s2.setAge("12");
        s2.setName("12121");

        Student s3 = new Student();
        s3.setAge("12");
        s3.setName("12121");

        studentArrayList.add(s);
        studentArrayList.add(s1);
        studentArrayList.add(s2);
        studentArrayList.add(s3);
    return  studentArrayList;
    }


    private DisposableObserver<String> getObserver() {

        rxObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

    return rxObserver;
    }


    private DisposableObserver<Student> getStudentObserver() {

        rxObserver2 = new DisposableObserver<Student>() {
            @Override
            public void onNext(Student s) {
                Toast.makeText(getApplicationContext(),s.age,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        return rxObserver2;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }







    class Student {
        String name,age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }


    class Student1 {
        String name,age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }

}
