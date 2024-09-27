grammar QL;

// Define the basic elements of the language as lexer rules
QOUTED_STRING: '"' ( ~'"' | '""' )* '"';
STRING: [a-zA-Z0-9_][a-zA-Z0-9_]*;
INT: [0-9]+;
FLOAT: [0-9]+ '.' [0-9]+;
WS: [ \t\r\n]+ -> skip;

// Define the parser rules for the commands
command: match_query | crud_command | database_command;

crud_command: node_command | edge_command | index_command;

node_command: create_node | delete_node | update_node;
edge_command: create_edge | delete_edge | update_edge;
index_command: create_index | delete_index;

create_node: 'CREATE' 'NODE' '(' id ':' label? properties? ')';
create_edge: 'CREATE' 'EDGE' label? 'FROM' sourceId 'TO' destinationId ('WITH' properties)?;
create_index: 'CREATE' 'INDEX' 'ON' key;

delete_node: 'DELETE' 'NODE' id;
delete_edge: 'DELETE' 'EDGE' label? 'FROM' sourceId 'TO' destinationId;
delete_index: 'DELETE' 'INDEX' 'ON' key;

update_node: 'UPDATE' 'NODE' id set_clause;
update_edge: 'UPDATE' 'EDGE' label? 'FROM' sourceId 'TO' destinationId set_clause;

sourceId: id;
destinationId: id;
set_clause: 'SET' set_item (',' set_item)*;
set_item: key '=' value;
properties: '{' property (',' property)* '}';
property: key ':' value;

label: STRING;
key: STRING;
value: INT | FLOAT | QUOTED_STRING | STRING;
id: STRING;

database_command: create_database | delete_database | drop_database | switch_database | get_current_database | switch_database_to_default | drop_default_database;
create_database: 'CREATE' 'DATABASE' database_name;
delete_database: 'DELETE' 'DATABASE' database_name;
drop_database: 'DROP' 'DATABASE' database_name;
switch_database: 'SWITCH' 'DATABASE' database_name;
get_current_database: 'GET' 'CURRENT' 'DATABASE';
switch_database_to_default: 'SWITCH' 'DATABASE' 'TO' 'DEFAULT';
drop_default_database: 'DROP' 'DEFAULT' 'DATABASE';
database_name: STRING;

match_query: 'MATCH' (path_query | shortest_path_query);
shortest_path_query: 'SHORTEST' 'PATH' 'FROM' sourceId 'TO' destinationId 'WITH COST =' cost (heuristic)?;

cost: ' ';
heuristic: 'USING HEURISTIC' heuristic_function;
heuristic_function: manhattan | euclidean;
manhattan: 'MANHATTAN' '(' 'x =' first_variable ',' 'y =' second_variable ')';
euclidean: 'EUCLIDEAN' '(' 'x =' first_variable ',' 'y =' second_variable ')';
first_variable: variable;
second_variable: variable;

path_query: path where_clause? return_clause;
path: starting_node (path_level)*;
starting_node: node_binding;
path_level: edge_binding node_binding;

node_binding: '[(' id? (':' label)? selectOperators? ')' alias? ']';
alias: 'AS' variable;
variable: STRING;

edge_binding: out_edge_binding | in_edge_binding;
out_edge_binding: '-' label? ('WITH' selectOperators)? alias? '->';
in_edge_binding: '<-' label? ('WITH' selectOperators)? alias? '-';

selectOperators: '{' selectOperator (',' selectOperator)* '}';
selectOperator: fieldName operator fieldValue;

fieldName: STRING;
fieldValue: INT | FLOAT | QOUTED_STRING | STRING;
operator: '=' | '<>' | '>' | '<=' | '>=';

where_clause: 'WHERE';
return_clause: 'RETURN' return_item (',' return_item)*;
return_item: variable | variable '.' fieldName;

// Define the entry point for the parser
start: command;
