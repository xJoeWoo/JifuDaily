package ng.jifudaily;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void Test() throws Exception {


        for (Constructor c : a.class.getConstructors()) {
            c.getParameterTypes();
        }

//
//        try {
//            ServiceComponent s = DaggerServiceComponent.create();
//            ServicesContainer c = new ServicesContainer();
//            s.inject(c);
//
//            c.getServices().daily().getLatestNews(new SubscriberAdapter<LatestNewsEntity>() {
//
//                @Override
//                public void onSubscribe(Subscription s) {
//                    System.out.println("S");
//                    super.onSubscribe(s);
//                }
//
//                @Override
//                                public void onComplete() {
//                    System.out.println("Com");
//
//                }
//
//                @Override
//                public void onNext(LatestNewsEntity latestNewsBean) {
//                    System.out.println(latestNewsBean.getStories().createContainer(1).getTitle());
//                }
//
//                @Override
//                public void onError(Throwable t) {
//                    System.out.println(t.getMessage());
//                }
//            });
//            Thread.sleep(2000);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//
//        }


    }

    class a {
        public a(Map<String, String> dd) {

        }
    }
}