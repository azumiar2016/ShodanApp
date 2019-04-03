package prototype.shodanappprototype;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.style.BackgroundColorSpan;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class GettingStartedActivity extends AppIntro {
    String PreviousActivity = "login";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        try{
            PreviousActivity = extras.getString("EXTRAS_Activity");
        }catch (Exception e){

        }

        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
     /*   addSlide(firstFragment);
        addSlide(secondFragment);
        addSlide(thirdFragment);
        addSlide(fourthFragment);
*/
        // Instead of fragments, you can also use our default slide.
        // Just create a `SliderPage` and provide title, description, background and image.
        // AppIntro will do the rest.
        SliderPage sliderPage = new SliderPage();
        String title = "Getting Started Tutorial";
        sliderPage.setTitle(title);
        String description = "This is a little introduction about Shodan.io and this app. You will see this only once at first launch!";
        sliderPage.setDescription(description);
        sliderPage.setBgColor(Color.green(155));
        addSlide(AppIntroFragment.newInstance(sliderPage));
        addSlide(SampleSlide.newInstance(R.layout.introslide_1));
        addSlide(SampleSlide.newInstance(R.layout.introslide_2));
        addSlide(SampleSlide.newInstance(R.layout.introslide_3));
        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#f22197"));
        setSeparatorColor(Color.parseColor("#f22197"));


        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(false);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
       /* startActivity(new Intent(GettingStartedActivity.this, LoginActivity.class));
        this.finish();
        */
        if(PreviousActivity.equals("About")){
            startActivity(new Intent(GettingStartedActivity.this, AboutActivity.class));
            this.finish();
        }else{
            startActivity(new Intent(GettingStartedActivity.this, LoginActivity.class));
            this.finish();
        }



    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
      /*  startActivity(new Intent(GettingStartedActivity.this, LoginActivity.class));
        this.finish();
        */

        if(PreviousActivity.equals("About")){
            startActivity(new Intent(GettingStartedActivity.this, AboutActivity.class));
            this.finish();
        }else{
            startActivity(new Intent(GettingStartedActivity.this, LoginActivity.class));
            this.finish();
        }


    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}


