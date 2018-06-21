# quick-api

## Structure

    ├── api                     # Project Folders for API Endpoints
    |   ├── employees           # MySQL example employees database API.
    ├── dev-env                 # Developer environment via vagrant
    ├── frontend                # HTML, js files
    ├── python                  # Utility scripts
	
## Notes

* Be sure to use git bash in ConEmu for the dev-startup.sh to work.

#### APIs

**(Simple JDBC) Employees** - https://dev.mysql.com/doc/employee/en/
* Test by using `>mvn test` in the /api/employees/ directory. Try not to test any other way else the unstoppable problem may rear its ugly head!

| Action | Endpoint         | Route                                                        |
|--------|------------------|--------------------------------------------------------------|
| Create | POST /employees/ | Route::post('employees/, 'EmployeeController.create');       |
| Read   | GET /employees/X | Route::get('employees/{emp_no}, 'EmployeeController.read');  |


#### Developer Environment

* https://app.vagrantup.com/donrwalsh/boxes/quick-api
* deploy.sh migrates static frontend files to dev server.


#### Frontend

* Basic HTML file with jQuery CDN call and fallback to minified version. 3.3.1.

#### Python Utility Scripts

* Connects to the database. Woohoo!
* Maybe this isn't necessary.

## TODOs

#### APIs

**Employees**

* Would be nice if the debugMessage only shows up in Dev.
* CRUD endpoints are functional. Am now working on basic forms for the frontend to consume them.

* Re-imagine the API table above.
* Inside the Employee object itself, there is a new @JsonCreator annotation that is apparently critical to the test functionality added recently. Need to learn more about what this is actually doing and why it's so structurally important.
* The current tests are running api calls (actual API calls) against the local database, which could cause problems as the two databases become more and more divergent. This could be fixed by pointing to the dev database, but ultimately tests should be mocking all this nonsense, so expanding the tests to be a little more correctly formed is the proper approach.
* As of right now, the validation for create calls not containing emp_nos just checks for the default value. This leads to a case where someone can use emp_no=0 with no error. Not a big issue, but would like to solve for completion.
* Date offset by 1? what?!
* Requesting generic input instead of an Employee -> may not be necessary.
* Revisiting the table, auto-increment, foreign keys -> this was done but not retained. -> Had to recreated on prod which is messy.
* At some point, the hello-world endpoint and test need to go.

#### Developer Environment

* MySQL Workbench connects to dev box, but it is a local configuration. How to capture this?
* Copying WAR files to the server.
* 192.163.33.10:8080 works, but quick-api-dev.com does not even after hostsfile has been set up normally. How to configure the hosts file?
* Unable to get java 8 to install silently. Guidance from google and stackoverflow doesn't work, perhaps because I'm utilizing vagrant.
* Getting the following error after `apt-get update and apt-get upgrade`:

`#E: Could not open lock file /var/lib/dpkg/lock - open (13: Permission denied)`

`#E: Unable to lock the administration directory (/var/lib/dpkg/), are you root?`

#### Prod Environment
* Have to use SSH Keys instead of utilizing the password during the script.
	