# quick-api

## Structure

    ├── api                     # Project Folders for API Endpoints
    |   ├── JDBC                # MySQL example employees database API. Simple JDBC Implementation
    |   ├── JDBC_T              # MySQL example employees database API. JDBC Template Implementation
    ├── dev-env                 # Developer environment via vagrant
    ├── frontend                # HTML, js files
    ├── python                  # Utility scripts

## TODO:

OG todo is getting out of hand. These are primary action items:

* CI/CD, Jenkins, Artifactery, Ansible

* Kill the Python folder.
* Organize the readme.
* Kill prod_deploy.sh

* RE: deploy.sh decide what to do with verbosity.
	
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

#### Frontend

* Had to awkwardly select emp_no by designating type of endpoint with a capital char at the start of the class, otherwise it will always pick the first 'emp_no' input element. There's gotta be a more elegant way to do this.
* DELETE endpoint returns no response + status of 204, which I guess is correct, but there's no output to show. That's weird and I'd like a better confirmation that the delete was actually processed.
* If something goes wrong it throws a simple 'failure' alert. Need better error handling.

#### APIs

**Employees**

* As of now, JDBC and JDBC_T exist side-by-side. That means I need to process non-implementation-related changes on both of them. This will quickly get out of hand, and I need to find a way to hopefully automate this, or even just a good way to process shared changes.

* Would be nice if the debugMessage only shows up in Dev.
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
	