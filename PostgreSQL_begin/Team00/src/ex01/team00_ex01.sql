WITH RECURSIVE 
last_step as (
	select cr.point1, cr.point2, cr.cost
    FROM tsp cr
    WHERE cr.point2 = 'a'
), 
possible_route AS (
	SELECT cr.point2, cr.point1 || ',' ||  cr.point2 AS route, cr.cost
    FROM tsp cr
    WHERE cr.point1 = 'a'
 	UNION ALL
 	SELECT cr.point2, pr.route || ',' ||  cr.point2 AS route, CAST((pr.cost + cr.cost) AS int)
    FROM possible_route pr
	INNER JOIN tsp cr ON cr.point1 = pr.point2 
  	and pr.route not like '%'||cr.point2||'%'
), 
tmp as (
SELECT 
'{'||pr.route || ',' || cr.point2||'}' as tour, 
CAST((pr.cost + cr.cost) AS int) as total_cost
FROM possible_route pr
join last_step cr
on pr.route like '%'||cr.point1
),
tt as (
select 
  tmp.total_cost,
  tmp.tour  
from tmp
where length(tmp.tour) = (select max(length(tmp.tour)) from tmp) 
)
select 
  tt.total_cost,
  tt.tour  
from tt
where tt.total_cost = (select min(tt.total_cost) from tt)
union
select 
  tt.total_cost,
  tt.tour  
from tt
where tt.total_cost = (select max(tt.total_cost) from tt)
order by 1,2
