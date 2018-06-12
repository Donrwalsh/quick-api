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

* As of right now, the validation for create calls not containing emp_nos just checks for the default value. This leads to a case where someone can use emp_no=0 with no error. Not a big issue, but would like to solve for completion.
* Date offset by 1? what?!
* Manually altered the emp_no column to be auto-increment. I'd like this to be present when I refresh the database as well.
* Finish the update endpoint
| * Currently the update endpoint tries to update the emp_no and fails, this needs to be changed at the database level.
* Requesting generic input instead of an Employee
* Error handling moved away from the Controller
* Revisiting the table, auto-increment, foreign keys

**Employees**
* Database is established and shell API is in place. Time to rig it up.

#### Developer Environment

* MySQL Workbench connects to dev box, but it is a local configuration. How to capture this?
* Copying WAR files to the server.
* 192.163.33.10:8080 works, but quick-api-dev.com does not even after hostsfile has been set up normally. How to configure the hosts file?
* Unable to get java 8 to install silently. Guidance from google and stackoverflow doesn't work, perhaps because I'm utilizing vagrant.
* Getting the following error after `apt-get update and apt-get upgrade`:

`#E: Could not open lock file /var/lib/dpkg/lock - open (13: Permission denied)`

`#E: Unable to lock the administration directory (/var/lib/dpkg/), are you root?`


	