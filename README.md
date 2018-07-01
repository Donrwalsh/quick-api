# quick-api

## Structure

    ├── api                     # API Endpoints
    |   ├── JDBC                # Simple JDBC CRUD API Implementation
    |   ├── JDBC_T              # JDBC Template CRUD API Implementation
    ├── dev-env                 # Developer environment via vagrant
    ├── frontend                # HTML, js files

#### api/

** Employees** - https://dev.mysql.com/doc/employee/en/

Currently all APIs work with 'Employees' data.

#### dev-env/

* Manual config with initiate.sh utilizing https://app.vagrantup.com/donrwalsh/boxes/quick-api.
* deploy.sh is a standin deploy script to be replaced by the pipeline.

#### frontend/

* Basic HTML files with javascript and jQuery support.
* Existing approach is not tied to any sort of concrete decision and is used for tinkering with vanilla frontend features.

## TODO, Active:

* CI/CD Configuration:
	* Jenkins
	* Ansible

## TODO:

* Round-out Karate tests.
* Unit tests?
* API Documentation
* JDBC_T and JDBC different handling of errors on empty PUT call. Why?
	