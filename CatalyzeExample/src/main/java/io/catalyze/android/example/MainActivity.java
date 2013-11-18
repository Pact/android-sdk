package io.catalyze.android.example;

//import java.util.Date;



import android.app.Activity;
//import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;
import android.view.Menu;
import android.view.View;
//import android.widget.Button;
import android.widget.EditText;
//import android.widget.TextView;

import io.catalyze.sdk.android.*;
import io.catalyze.sdk.android.user.Gender;
import io.catalyze.sdk.android.user.ZipCode;

public class MainActivity extends Activity {

	private MyApplication mMyApplication;
	private CatalyzeUser mUser;
	private Catalyze catalyze;
	private CustomClass customClass;
	private static final String APIKey = "1ca769ce-07b3-40bf-95c0-2feda3e3c909";
	//private Object synchObj = new Object();										
	private Query query;
	private static final String api = "1f077962-18cc-4ade-8075-d9fa1642f316";
	private static final String identifier = "android.example";
	private CatalyzeUser otherUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mMyApplication = (MyApplication) getApplication();
		mMyApplication.getRequestQueue().start();

		catalyze = new Catalyze(api, identifier, MainActivity.this);
		// Catalyze.setAppID(AppId);

		findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText mEdit = (EditText) findViewById(R.id.userNameTextField);
				String username = mEdit.getText().toString();
				mEdit = (EditText) findViewById(R.id.passwordTextField);

				catalyze.getUser(username, mEdit.getText().toString(),
						newUserResponseHandler());
			}
		});

		findViewById(R.id.logout).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						catalyze.logoutCurrentUser(newResponseHandler());
					}
				});

		findViewById(R.id.GetUser).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
//						String classname = "ccTest1";
						
						if(customClass == null) customClass = CustomClass.getInstance("user2", mUser);
						customClass.getEntry("528653c7117079bb586e5aa7", newCCHandler());
						
//						catalyze.lookupUser("test@user.com", lookupUserHandler());
//						
//						JSONObject schema = new JSONObject();
//						try {
//							schema.put("name", "string");
//							schema.put("occupation", "string");
//							schema.put("location", "string");
//							schema.put("age", "integer");
//						} catch (JSONException e) {
//
//						}
//						
						//cc.get("MyNewClass", newCCHandler());
						//cc.getEntry("MyNewClass", "5282742e11709322bbbb9e37", newCCHandler());
//						customClass.putContent("city", "chicago");
//						customClass.updateEntry("MyNewClass", "5282ad77117003b47fad4c00", newCCHandler());
//						cc.delete(classname, newCCHandler());
//						JSONObject newInstance = new JSONObject();
//						try {
//							newInstance.put("name", "philip");
//							newInstance.put("occupation", "barber");
//							newInstance.put("location", "somewhere");
//							newInstance.put("age", 55);
//						} catch (JSONException e) {
//
//						}
						//cc.createCustomClass(classname, false, schema, newCCHandler());
						//cc.addInstance(classname, newInstance, newCCHandler());
						//cc.get(classname, newCCHandler());

						// CustomClass.addInstance("customClassTest2",
						// newInstance, catalyze, newCCHandler());

						// JSONObject schema = new JSONObject();
						// try {
						// schema.put("name", "string");
						// schema.put("occupation", "string");
						// schema.put("location", "string");
						// schema.put("age", "integer");
						// } catch (JSONException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// }
						//
						//
						// CustomClass cc = new CustomClass("customClassTest1",
						// false, schema, catalyze, newCCHandler());

						// CustomClass.getInstance(catalyze, "customClassTest",
						// newCCHandler());						
					}
				});
		findViewById(R.id.signUp).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						EditText mEdit = (EditText) findViewById(R.id.userNameTextField);

						String username = mEdit.getText().toString();
						mEdit = (EditText) findViewById(R.id.passwordTextField);
						catalyze.signUp(username, mEdit.getText().toString(),
								"John", "johnson", newUserResponseHandler());

					}
				});
		
		findViewById(R.id.superUser).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						//catalyze.lookupUser("test@user.com", newUserResponseHandler());
//						otherUser.setFirstName("asdfghjkl");
//						catalyze.updateUser(otherUser, newUserResponseHandler());
//						catalyze.searchForUser("test", userSearchHandler()); 
						UMLS u = new UMLS(catalyze);
						//u.getCodesetList(userSearchHandler());
						//u.getValueSetList(userSearchHandler());
						//u.codeLookup("SNOMEDCT", "244329001", listenForUmlsResults());
						u.valueLookup("city", "1581834", listenForUmlsResult());
						u.searchByKeyword("rxnorm", "Acetaminophen", listenForUmlsResults());
						
						//u.searchByCodeOrConcept("concept", "snomedct", "244329001", listenForUmlsResults());
						//u.searchByPrefix("snomedct", "acet", listenForUmlsResults());
						
					}
				});
		findViewById(R.id.update).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// catalyze.getUser("test@user.com", "testpass",
						// newUserResposneHandler());

						//mUser.setStreet("1 E Main St");
						mUser.setAge(55);
						mUser.setCity("Madison");
						mUser.setState("Wisconsin");
						mUser.setDateOfBirth("1990-07-18");
						mUser.setCountry("US");
						mUser.setGender(Gender.MALE);
						mUser.setPhoneNumber("9876543210");
						mUser.setZipCode(new ZipCode("54321"));
						mUser.update(newUserResponseHandler());
			}
		});

		findViewById(R.id.customClasses).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(customClass == null) customClass = CustomClass.getInstance("user2", mUser);
//				
				//cc.query("ccTest1", newCCHandler());
//				Query q = new Query("MyNewClass");
//				q.setField("");
//				q.setPageNumber(1);
//				q.setSearchBy("");
//				q.setPageSize(25);
//				q.executeQuery(catalyze, newQueryHandler());
//				customClass.getEntry("MyNewClass", "5282ad77117003b47fad4c00", newCCHandler());
//				customClass.getArrayRef("user", "5284ff081170cc2a30370937", "address", "5284fe141170cc2a30370935", newCCHandler());
				customClass.getArray("visits", newCCArrayHandler());
				//customClass.deleteArrayRef("user", "5284ff081170cc2a30370937", "address", "5284fe141170cc2a30370935", newCCHandler());
				//customClass.addReferenceArray("user2", "528653c7117079bb586e5aa7", "visits", "528660e6117079bb586e5abb", newCCHandler());
				// cc.get("ccTest1", newCCHandler());
//				JSONObject newInstance = new JSONObject();
//				try {
//					newInstance.put("name", "philip");
//					newInstance.put("occupation", "barber");
//					newInstance.put("location", "somewhere");
//					newInstance.put("age", 55);
//				} catch (JSONException e) {
//
//				}
				//cc.addEntry("ccTest2" + "", newInstance, newCCHandler());
			}
		});

		findViewById(R.id.DeleteUser).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						catalyze.deleteCurrentUser(newResponseHandler());
					}
				});

	}

	private CatalyzeListener<CatalyzeUser> newUserResponseHandler() {
		return new CatalyzeListener<CatalyzeUser>() {

			@Override
			public void onError(CatalyzeError response) {
				// TODO Auto-generated method stub
				System.out.println("SOMETHING WENT WRONG");
				mUser.toString();
			}

			@Override
			public void onSuccess(CatalyzeUser response) {
				// TODO Auto-generated method stub
				mUser = response;
				String s = "Successful operation!!!";
				System.out.println(s);
			}
		};
	}

	private CatalyzeListener<CatalyzeUser> newResponseHandler() {
		return new CatalyzeListener<CatalyzeUser>() {

			@Override
			public void onError(CatalyzeError response) {
				// TODO Auto-generated method stub
				System.out.println("SOMETHING WENT WRONG");
			}

			@Override
			public void onSuccess(CatalyzeUser response) {
				// TODO Auto-generated method stub

			}
		};
	}

	private CatalyzeListener<CatalyzeUser> lookupUserHandler() {
		return new CatalyzeListener<CatalyzeUser>() {

			@Override
			public void onError(CatalyzeError response) {
				// TODO Auto-generated method stub
				System.out.println("SOMETHING WENT WRONG");
			}

			@Override
			public void onSuccess(CatalyzeUser response) {
				otherUser = response;

			}
		};
	}
	
	private CatalyzeListener<String[]> userSearchHandler() {
		return new CatalyzeListener<String[]>() {

			@Override
			public void onError(CatalyzeError response) {
				// TODO Auto-generated method stub
				System.out.println("SOMETHING WENT WRONG");
			}

			@Override
			public void onSuccess(String[] response) {
				String[] results = response;

			}
		};
	}
	
	private CatalyzeListener<UmlsResult[]> listenForUmlsResults() {
		return new CatalyzeListener<UmlsResult[]>() {

			@Override
			public void onError(CatalyzeError response) {
				System.out.println("SOMETHING WENT WRONG");
			}

			@Override
			public void onSuccess(UmlsResult[] response) {
				UmlsResult[] results = response;

			}
		};
	}
	
	private CatalyzeListener<UmlsResult> listenForUmlsResult() {
		return new CatalyzeListener<UmlsResult>() {

			@Override
			public void onError(CatalyzeError response) {
				System.out.println("SOMETHING WENT WRONG");
			}

			@Override
			public void onSuccess(UmlsResult response) {
				UmlsResult results = response;

			}
		};
	}
	
	private CatalyzeListener<CustomClass> newCCHandler() {
		return new CatalyzeListener<CustomClass>() {

			@Override
			public void onError(CatalyzeError response) {
				System.out.println("SOMETHING WENT WRONG");
			}

			@Override
			public void onSuccess(CustomClass response) {
				customClass = response;
			}

		};
	}
	
	private CatalyzeListener<CustomClass[]> newCCArrayHandler() {
		return new CatalyzeListener<CustomClass[]>() {

			@Override
			public void onError(CatalyzeError response) {
				System.out.println("SOMETHING WENT WRONG");
			}

			@Override
			public void onSuccess(CustomClass[] response) {
				CustomClass[] a = response;
			}

		};
	}
	
	private CatalyzeListener<Query> newQueryHandler() {
		return new CatalyzeListener<Query>() {

			@Override
			public void onError(CatalyzeError response) {
				System.out.println("SOMETHING WENT WRONG");
			}

			@Override
			public void onSuccess(Query response) {
				query = response;
				System.out.println("Query response completed succesfully");
			}

		};
	}
	
	private CatalyzeListener<CustomClass> newCCHandlerVoidResponse() {
		return new CatalyzeListener<CustomClass>() {

			@Override
			public void onError(CatalyzeError response) {
				System.out.println("SOMETHING WENT WRONG");
			}

			@Override
			public void onSuccess(CustomClass response) {
				customClass = response;
			}

		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
