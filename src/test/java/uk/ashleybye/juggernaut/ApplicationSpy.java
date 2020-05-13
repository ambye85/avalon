package uk.ashleybye.juggernaut;

public class ApplicationSpy implements Application {

  private boolean initialised;

  public boolean wasInitialised() {
    return initialised;
  }

  @Override
  public void initialise() {
    initialised = true;
  }
}
